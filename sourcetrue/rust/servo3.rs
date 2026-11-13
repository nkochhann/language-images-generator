mod platform {
    pub use std::alloc::System as Allocator;
    use std::os::raw::c_void;

    use windows_sys::Win32::Foundation::FALSE;
    use windows_sys::Win32::System::Memory::{GetProcessHeap, HeapSize, HeapValidate};

    /// Get the size of a heap block.
    ///
    /// # Safety
    ///
    /// Passing a non-heap allocated pointer to this function results in undefined behavior.
    pub unsafe extern "C" fn usable_size(mut ptr: *const c_void) -> usize {
        unsafe {
            let heap = GetProcessHeap();

            if HeapValidate(heap, 0, ptr) == FALSE {
                ptr = *(ptr as *const *const c_void).offset(-1)
            }

            let size = HeapSize(heap, 0, ptr) as usize;
            #[cfg(feature = "allocation-tracking")]
            crate::ALLOC.note_allocation(ptr, size);
            size
        }
    }

    pub fn heap_reports() -> Vec<crate::HeapReport> {
        Vec::new()
    }
}