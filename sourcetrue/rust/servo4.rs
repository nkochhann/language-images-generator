pub const fn with_allocator(allocator: A) -> Self {
    Self {
        allocator,
        enabled: AtomicBool::new(true),
    }
}

pub(crate) fn disable(&self) {
    self.enabled.store(false, Ordering::SeqCst);
}

fn enabled(&self) -> bool {
    self.enabled.load(Ordering::SeqCst)
}

#[expect(clippy::absurd_extreme_comparisons)]
fn remove_allocation(&self, ptr: *const c_void, size: usize) {
    if size < MIN_SIZE {
        return;
    }
    let old = IN_ALLOCATION.with(|status| status.replace(true));
    if old {
        return;
    }
    let mut sites = ALLOCATION_SITES.lock().unwrap();
    if let Entry::Occupied(e) = sites.entry(ptr as usize) {
        e.remove();
    }
    IN_ALLOCATION.with(|status| status.set(old));
}