int fscrypt_symlink_getattr(const struct path *path, struct kstat *stat)
{
	struct dentry *dentry = path->dentry;
	struct inode *inode = d_inode(dentry);
	const char *link;
	DEFINE_DELAYED_CALL(done);

	/*
	 * To get the symlink target that userspace will see (whether it's the
	 * decrypted target or the no-key encoded target), we can just get it in
	 * the same way the VFS does during path resolution and readlink().
	 */
	link = READ_ONCE(inode->i_link);
	if (!link) {
		link = inode->i_op->get_link(dentry, inode, &done);
		if (IS_ERR(link))
			return PTR_ERR(link);
	}
	stat->size = strlen(link);
	do_delayed_call(&done);
	return 0;
}
EXPORT_SYMBOL_GPL(fscrypt_symlink_getattr);