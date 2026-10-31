#include <linux/export.h>
#include <linux/fs_context.h>
#include <linux/mount.h>
#include <linux/random.h>
#include <linux/seq_file.h>
#include <linux/string.h>

#include "fscrypt_private.h"

/**
 * fscrypt_policies_equal() - check whether two encryption policies are the same
 * @policy1: the first policy
 * @policy2: the second policy
 *
 * Return: %true if equal, else %false
 */
bool fscrypt_policies_equal(const union fscrypt_policy *policy1,
			    const union fscrypt_policy *policy2)
{
	if (policy1->version != policy2->version)
		return false;

	return !memcmp(policy1, policy2, fscrypt_policy_size(policy1));
}

int fscrypt_policy_to_key_spec(const union fscrypt_policy *policy,
			       struct fscrypt_key_specifier *key_spec)
{
	switch (policy->version) {
	case FSCRYPT_POLICY_V1:
		key_spec->type = FSCRYPT_KEY_SPEC_TYPE_DESCRIPTOR;
		memcpy(key_spec->u.descriptor, policy->v1.master_key_descriptor,
		       FSCRYPT_KEY_DESCRIPTOR_SIZE);
		return 0;
	case FSCRYPT_POLICY_V2:
		key_spec->type = FSCRYPT_KEY_SPEC_TYPE_IDENTIFIER;
		memcpy(key_spec->u.identifier, policy->v2.master_key_identifier,
		       FSCRYPT_KEY_IDENTIFIER_SIZE);
		return 0;
	default:
		WARN_ON_ONCE(1);
		return -EINVAL;
	}
}