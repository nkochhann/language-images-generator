static int keyring_store(struct credential *c)
{
	char *label = NULL;
	GHashTable *attributes = NULL;
	GError *error = NULL;
	GString *secret = NULL;

	/*
	 * Sanity check that what we are storing is actually sensible.
	 * In particular, we can't make a URL without a protocol field.
	 * Without either a host or pathname (depending on the scheme),
	 * we have no primary key. And without a username and password,
	 * we are not actually storing a credential.
	 */
	if (!c->protocol || !(c->host || c->path) ||
	    !c->username || !c->password)
		return EXIT_FAILURE;

	label = make_label(c);
	attributes = make_attr_list(c);
	secret = g_string_new(c->password);
	if (c->password_expiry_utc) {
		g_string_append_printf(secret, "\npassword_expiry_utc=%s",
			c->password_expiry_utc);
	}
	if (c->oauth_refresh_token) {
		g_string_append_printf(secret, "\noauth_refresh_token=%s",
			c->oauth_refresh_token);
	}
	secret_password_storev_sync(&schema,
				    attributes,
				    NULL,
				    label,
				    secret->str,
				    NULL,
				    &error);
	g_string_free(secret, TRUE);
	g_free(label);
	g_hash_table_unref(attributes);

	if (error != NULL) {
		g_critical("store failed: %s", error->message);
		g_error_free(error);
		return EXIT_FAILURE;
	}

	return EXIT_SUCCESS;
}