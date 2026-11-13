static void credential_write_item(FILE *fp, const char *key, const char *value)
{
	if (!value)
		return;
	fprintf(fp, "%s=%s\n", key, value);
}

static void credential_write(const struct credential *c)
{
	/* only write username/password, if set */
	credential_write_item(stdout, "username", c->username);
	credential_write_item(stdout, "password", c->password);
	credential_write_item(stdout, "password_expiry_utc",
		c->password_expiry_utc);
	credential_write_item(stdout, "oauth_refresh_token",
		c->oauth_refresh_token);
}

static void usage(const char *name)
{
	struct credential_operation const *try_op = credential_helper_ops;
	const char *basename = strrchr(name, '/');

	basename = (basename) ? basename + 1 : name;
	fprintf(stderr, "usage: %s <", basename);
	while (try_op->name) {
		fprintf(stderr, "%s", (try_op++)->name);
		if (try_op->name)
			fprintf(stderr, "%s", "|");
	}
	fprintf(stderr, "%s", ">\n");
}