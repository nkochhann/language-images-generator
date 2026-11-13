int fscrypt_file_open(struct inode *inode, struct file *filp)
{
	int err;
	struct dentry *dentry, *dentry_parent;
	struct inode *inode_parent;

	err = fscrypt_require_key(inode);
	if (err)
		return err;

	dentry = file_dentry(filp);

	/*
	 * Getting a reference to the parent dentry is needed for the actual
	 * encryption policy comparison, but it's expensive on multi-core
	 * systems.  Since this function runs on unencrypted files too, start
	 * with a lightweight RCU-mode check for the parent directory being
	 * unencrypted (in which case it's fine for the child to be either
	 * unencrypted, or encrypted with any policy).  Only continue on to the
	 * full policy check if the parent directory is actually encrypted.
	 */
	rcu_read_lock();
	dentry_parent = READ_ONCE(dentry->d_parent);
	inode_parent = d_inode_rcu(dentry_parent);
	if (inode_parent != NULL && !IS_ENCRYPTED(inode_parent)) {
		rcu_read_unlock();
		return 0;
	}
	rcu_read_unlock();

	dentry_parent = dget_parent(dentry);
	if (!fscrypt_has_permitted_context(d_inode(dentry_parent), inode)) {
		fscrypt_warn(inode,
			     "Inconsistent encryption context (parent directory: %llu)",
			     d_inode(dentry_parent)->i_ino);
		err = -EPERM;
	}
	dput(dentry_parent);
	return err;
}