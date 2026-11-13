 if (ngx_rename_file(ccf->pid.data, ccf->oldpid.data) == NGX_FILE_ERROR) {
    ngx_log_error(NGX_LOG_ALERT, cycle->log, ngx_errno,
                    ngx_rename_file_n " %s to %s failed "
                    "before executing new binary process \"%s\"",
                    ccf->pid.data, ccf->oldpid.data, argv[0]);

    ngx_free(env);
    ngx_free(var);

    return NGX_INVALID_PID;
}

pid = ngx_execute(cycle, &ctx);

if (pid == NGX_INVALID_PID) {
    if (ngx_rename_file(ccf->oldpid.data, ccf->pid.data)
        == NGX_FILE_ERROR)
    {
        ngx_log_error(NGX_LOG_ALERT, cycle->log, ngx_errno,
                        ngx_rename_file_n " %s back to %s failed after "
                        "an attempt to execute new binary process \"%s\"",
                        ccf->oldpid.data, ccf->pid.data, argv[0]);
    }
}