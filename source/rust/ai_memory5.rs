impl LatencyHistogram {
    /// Record one observation.
    pub fn observe(&self, elapsed: Duration) {
        let us = u64::try_from(elapsed.as_micros()).unwrap_or(u64::MAX);
        let idx = LATENCY_BUCKET_BOUNDS_US
            .iter()
            .position(|bound| us <= *bound)
            .unwrap_or(BUCKETS - 1);
        self.buckets[idx].fetch_add(1, Ordering::Relaxed);
        self.sum_us.fetch_add(us, Ordering::Relaxed);
    }

    /// Start a timer that records into this histogram when dropped, so every
    /// return path of the measured call is counted, errors included.
    #[must_use]
    pub fn start(&self) -> LatencyTimer<'_> {
        LatencyTimer {
            histogram: self,
            started: Instant::now(),
        }
    }

    /// Read the histogram: per-bucket counts (non-cumulative, `+Inf` last)
    /// and the total observed microseconds.
    #[must_use]
    pub fn snapshot(&self) -> LatencySnapshot {
        let mut buckets = [0_u64; BUCKETS];
        for (slot, bucket) in buckets.iter_mut().zip(&self.buckets) {
            *slot = bucket.load(Ordering::Relaxed);
        }
        LatencySnapshot {
            buckets,
            sum_us: self.sum_us.load(Ordering::Relaxed),
        }
    }
}