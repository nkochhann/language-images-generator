use std::{
    cmp, fmt,
    hash::{Hash, Hasher},
    iter::{Enumerate, FusedIterator},
    marker::PhantomData,
    ops::{Index, IndexMut, Range, RangeInclusive},
};

mod map;
pub use map::{ArenaMap, Entry, OccupiedEntry, VacantEntry};

/// The raw index of a value in an arena.
#[derive(Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub struct RawIdx(u32);

impl RawIdx {
    /// Constructs a [`RawIdx`] from a u32.
    pub const fn from_u32(u32: u32) -> Self {
        RawIdx(u32)
    }

    /// Deconstructs a [`RawIdx`] into the underlying u32.
    pub const fn into_u32(self) -> u32 {
        self.0
    }
}