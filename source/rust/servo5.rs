pub(crate) fn enclosing_size(&self, ptr: *const c_void) -> (*const c_void, usize) {
    ALLOCATION_SITES
        .lock()
        .unwrap()
        .iter()
        .find(|(_, site)| site.contains(ptr.cast_mut().cast()))
        .map_or((std::ptr::null_mut(), 0), |(_, site)| {
            (site.ptr.cast(), site.size)
        })
}

// The default is an absurd comparisons but you are supposed to change the default.
#[expect(clippy::absurd_extreme_comparisons)]
pub(crate) fn note_allocation(&self, ptr: *const c_void, size: usize) {
    if size < MIN_SIZE {
        return;
    }
    IN_ALLOCATION.with(|status| status.set(true));
    if let Some(site) = ALLOCATION_SITES.lock().unwrap().get_mut(&(ptr as usize)) {
        site.noted = true;
    }
    IN_ALLOCATION.with(|status| status.set(false));
}