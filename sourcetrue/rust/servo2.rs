fn jemalloc_stat(value_name: &CStr) -> Option<usize> {
    // Before we request the measurement of interest, we first send an "epoch"
    // request. Without that jemalloc gives cached statistics(!) which can be
    // highly inaccurate.
    let epoch_c_name = c"epoch";
    let mut epoch: u64 = 0;
    let epoch_ptr = &raw mut epoch;
    let mut epoch_len = size_of_val(&epoch);

    let mut value: usize = 0;
    let value_ptr = &raw mut value;
    let mut value_len = size_of_val(&value);

    // Using the same values for the `old` and `new` parameters is enough
    // to get the statistics updated.
    let rv = unsafe {
        mallctl(
            epoch_c_name.as_ptr(),
            epoch_ptr.cast(),
            &mut epoch_len,
            epoch_ptr.cast(),
            epoch_len,
        )
    };
    if rv != 0 {
        return None;
    }

    let rv = unsafe {
        mallctl(
            value_name.as_ptr(),
            value_ptr.cast(),
            &mut value_len,
            ptr::null_mut(),
            0,
        )
    };
    if rv != 0 {
        return None;
    }

    Some(value)
}