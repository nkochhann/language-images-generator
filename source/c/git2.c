static const SecretSchema schema = {
	.name = "org.git.Password",
	/* Ignore schema name during search for backwards compatibility */
	.flags = SECRET_SCHEMA_DONT_MATCH_NAME,
	.attributes = {
		/*
		 * libsecret assumes attribute values are non-confidential and
		 * unchanging, so we can't include oauth_refresh_token or
		 * password_expiry_utc.
		 */
		{  "user", SECRET_SCHEMA_ATTRIBUTE_STRING },
		{  "object", SECRET_SCHEMA_ATTRIBUTE_STRING },
		{  "protocol", SECRET_SCHEMA_ATTRIBUTE_STRING },
		{  "port", SECRET_SCHEMA_ATTRIBUTE_INTEGER },
		{  "server", SECRET_SCHEMA_ATTRIBUTE_STRING },
		{  NULL, 0 },
	}
};

static char *make_label(struct credential *c)
{
	if (c->port)
		return g_strdup_printf("Git: %s://%s:%hu/%s",
					c->protocol, c->host, c->port, c->path ? c->path : "");
	else
		return g_strdup_printf("Git: %s://%s/%s",
					c->protocol, c->host, c->path ? c->path : "");
}

static GHashTable *make_attr_list(struct credential *c)
{
	GHashTable *al = g_hash_table_new_full(g_str_hash, g_str_equal, NULL, g_free);

	if (c->username)
		g_hash_table_insert(al, "user", g_strdup(c->username));
	if (c->protocol)
		g_hash_table_insert(al, "protocol", g_strdup(c->protocol));
	if (c->host)
		g_hash_table_insert(al, "server", g_strdup(c->host));
	if (c->port)
		g_hash_table_insert(al, "port", g_strdup_printf("%hu", c->port));
	if (c->path)
		g_hash_table_insert(al, "object", g_strdup(c->path));

	return al;
}