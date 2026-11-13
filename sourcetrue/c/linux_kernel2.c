nt fscrypt_prepare_setflags(struct inode *inode,
			     unsigned int oldflags, unsigned int flags)
{
	struct fscrypt_inode_info *ci;
	struct fscrypt_master_key *mk;
	int err;

	/*
	 * When the CASEFOLD flag is set on an encrypted directory, we must
	 * derive the secret key needed for the dirhash.  This is only possible
	 * if the directory uses a v2 encryption policy.
	 */
	if (IS_ENCRYPTED(inode) && (flags & ~oldflags & FS_CASEFOLD_FL)) {
		err = fscrypt_require_key(inode);
		if (err)
			return err;
		ci = fscrypt_get_inode_info_raw(inode);
		if (ci->ci_policy.version != FSCRYPT_POLICY_V2)
			return -EINVAL;
		mk = ci->ci_master_key;
		down_read(&mk->mk_sem);
		if (mk->mk_present)
			fscrypt_derive_dirhash_key(ci, mk);
		else
			err = -ENOKEY;
		up_read(&mk->mk_sem);
		return err;
	}
	return 0;
}