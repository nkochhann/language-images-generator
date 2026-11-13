/*
 * Table with helper operation callbacks, used by generic
 * credential helper main function.
 */
static struct credential_operation const credential_helper_ops[] = {
	{ "get",   keyring_get },
	{ "store", keyring_store },
	{ "erase", keyring_erase },
	CREDENTIAL_OP_END
};

/* ------------------ credential functions ------------------ */

static void credential_init(struct credential *c)
{
	memset(c, 0, sizeof(*c));
}

static void credential_clear(struct credential *c)
{
	g_free(c->protocol);
	g_free(c->host);
	g_free(c->path);
	g_free(c->username);
	g_free(c->password);
	g_free(c->password_expiry_utc);
	g_free(c->oauth_refresh_token);

	credential_init(c);
}