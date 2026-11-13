#[test]
fn every_skill_has_managed_marker() {
    assert!(!MANAGED_MARKER.is_empty());
    for skill in MANAGED_SKILLS {
        assert!(
            skill.content.contains(MANAGED_MARKER),
            "{} is missing the managed ownership marker",
            skill.name
        );
    }
}

#[test]
fn managed_skill_payloads_use_lf_line_endings() {
    for skill in MANAGED_SKILLS {
        assert!(
            !skill.content.contains('\r'),
            "{} must use LF line endings",
            skill.name
        );
        assert!(
            skill.content.ends_with('\n'),
            "{} must end with a newline",
            skill.name
        );
    }
}