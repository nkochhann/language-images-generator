impl<T, V> Iterator for ArenaMapIter<Idx<T>, V> {
    type Item = (Idx<T>, V);

    #[inline]
    fn next(&mut self) -> Option<Self::Item> {
        for next in self.iter.by_ref() {
            match Self::mapper(next) {
                Some(r) => return Some(r),
                None => continue,
            }
        }

        None
    }

    #[inline]
    fn size_hint(&self) -> (usize, Option<usize>) {
        self.iter.size_hint()
    }
}

impl<T, V> DoubleEndedIterator for ArenaMapIter<Idx<T>, V> {
    #[inline]
    fn next_back(&mut self) -> Option<Self::Item> {
        while let Some(next_back) = self.iter.next_back() {
            match Self::mapper(next_back) {
                Some(r) => return Some(r),
                None => continue,
            }
        }

        None
    }
}