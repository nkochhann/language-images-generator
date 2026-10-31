/// Create a new empty map with specific capacity.
pub fn with_capacity(capacity: usize) -> Self {
    Self { v: Vec::with_capacity(capacity), _ty: PhantomData }
}

/// Reserves capacity for at least additional more elements to be inserted in the map.
pub fn reserve(&mut self, additional: usize) {
    self.v.reserve(additional);
}

/// Clears the map, removing all elements.
pub fn clear(&mut self) {
    self.v.clear();
}

/// Shrinks the capacity of the map as much as possible.
pub fn shrink_to_fit(&mut self) {
    let min_len = self.v.iter().rposition(|slot| slot.is_some()).map_or(0, |i| i + 1);
    self.v.truncate(min_len);
    self.v.shrink_to_fit();
}

/// Returns whether the map contains a value for the specified index.
pub fn contains_idx(&self, idx: Idx<T>) -> bool {
    matches!(self.v.get(Self::to_idx(idx)), Some(Some(_)))
}

/// Removes an index from the map, returning the value at the index if the index was previously in the map.
pub fn remove(&mut self, idx: Idx<T>) -> Option<V> {
    self.v.get_mut(Self::to_idx(idx))?.take()
}