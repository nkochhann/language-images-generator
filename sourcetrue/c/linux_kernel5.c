static bool fscrypt_valid_enc_modes_v1(u32 contents_mode, u32 filenames_mode)
{
	if (contents_mode == FSCRYPT_MODE_AES_256_XTS &&
	    filenames_mode == FSCRYPT_MODE_AES_256_CTS)
		return true;

	if (contents_mode == FSCRYPT_MODE_AES_128_CBC &&
	    filenames_mode == FSCRYPT_MODE_AES_128_CTS)
		return true;

	if (contents_mode == FSCRYPT_MODE_ADIANTUM &&
	    filenames_mode == FSCRYPT_MODE_ADIANTUM)
		return true;

	return false;
}

static bool fscrypt_valid_enc_modes_v2(u32 contents_mode, u32 filenames_mode)
{
	if (contents_mode == FSCRYPT_MODE_AES_256_XTS &&
	    filenames_mode == FSCRYPT_MODE_AES_256_HCTR2)
		return true;

	if (contents_mode == FSCRYPT_MODE_SM4_XTS &&
	    filenames_mode == FSCRYPT_MODE_SM4_CTS)
		return true;

	return fscrypt_valid_enc_modes_v1(contents_mode, filenames_mode);
}