use std::os::raw::c_void;

#[cfg(not(feature = "allocation-tracking"))]
#[global_allocator]
static ALLOC: Allocator = Allocator;

#[cfg(feature = "allocation-tracking")]
#[global_allocator]
static ALLOC: crate::tracking::AccountingAlloc<Allocator> =
    crate::tracking::AccountingAlloc::with_allocator(Allocator);

#[cfg(feature = "allocation-tracking")]
mod tracking;

pub fn is_tracking_unmeasured() -> bool {
    cfg!(feature = "allocation-tracking")
}

pub fn dump_unmeasured(_writer: impl std::io::Write) {
    #[cfg(feature = "allocation-tracking")]
    ALLOC.dump_unmeasured_allocations(_writer);
}

pub fn disable_unmeasured_tracking() {
    #[cfg(feature = "allocation-tracking")]
    ALLOC.disable();
}

pub struct HeapReport {
    pub path: &'static str,
    pub size: Option<usize>,
}