if (ngx_conf_file) {
    cycle->conf_file.len = ngx_strlen(ngx_conf_file);
    cycle->conf_file.data = ngx_conf_file;

} else {
    ngx_str_set(&cycle->conf_file, NGX_CONF_PATH);
}

if (ngx_conf_full_name(cycle, &cycle->conf_file, 0) != NGX_OK) {
    return NGX_ERROR;
}

for (p = cycle->conf_file.data + cycle->conf_file.len - 1;
        p > cycle->conf_file.data;
        p--)
{
    if (ngx_path_separator(*p)) {
        cycle->conf_prefix.len = p - cycle->conf_file.data + 1;
        cycle->conf_prefix.data = cycle->conf_file.data;
        break;
    }
}