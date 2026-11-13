#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <glib.h>
#include <libsecret/secret.h>

/*
 * This credential struct and API is simplified from git's credential.{h,c}
 */
struct credential {
	char *protocol;
	char *host;
	unsigned short port;
	char *path;
	char *username;
	char *password;
	char *password_expiry_utc;
	char *oauth_refresh_token;
};

#define CREDENTIAL_INIT { 0 }

typedef int (*credential_op_cb)(struct credential *);

struct credential_operation {
	char *name;
	credential_op_cb op;
};

#define CREDENTIAL_OP_END { NULL, NULL }

static void credential_clear(struct credential *c);