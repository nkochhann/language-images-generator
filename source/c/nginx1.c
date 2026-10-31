if (cycle == NULL) {
    if (ngx_test_config) {
        ngx_log_stderr(0, "configuration file %s test failed",
                        init_cycle.conf_file.data);
    }

    return 1;
}

if (ngx_test_config) {
    if (!ngx_quiet_mode) {
        ngx_log_stderr(0, "configuration file %s test is successful",
                        cycle->conf_file.data);
    }

    if (ngx_dump_config) {
        cd = cycle->config_dump.elts;

        for (i = 0; i < cycle->config_dump.nelts; i++) {

            ngx_write_stdout("# configuration file ");
            (void) ngx_write_fd(ngx_stdout, cd[i].name.data,
                                cd[i].name.len);
            ngx_write_stdout(":" NGX_LINEFEED);

            b = cd[i].buffer;

            (void) ngx_write_fd(ngx_stdout, b->pos, b->last - b->pos);
            ngx_write_stdout(NGX_LINEFEED);
        }
    }

    return 0;
}