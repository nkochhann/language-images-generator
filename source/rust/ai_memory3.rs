#[test]
fn grok_handoff_rule_is_managed_and_mandates_first_turn_list() {
    assert_eq!(GROK_RULES_DIR, "rules");
    assert_eq!(GROK_HANDOFF_RULE_FILENAME, "ai-memory-handoff.md");
    assert!(!GROK_HANDOFF_RULE_MARKER.is_empty());
    assert!(GROK_HANDOFF_RULE_CONTENT.contains(GROK_HANDOFF_RULE_MARKER));
    assert!(!GROK_HANDOFF_RULE_CONTENT.contains('\r'));
    assert!(GROK_HANDOFF_RULE_CONTENT.ends_with('\n'));
    assert!(GROK_HANDOFF_RULE_CONTENT.contains("memory_handoff_list"));
    assert!(GROK_HANDOFF_RULE_CONTENT.contains("first tool"));
    assert!(GROK_HANDOFF_RULE_CONTENT.contains("static MCP client"));
    assert!(GROK_HANDOFF_RULE_CONTENT.contains("`workspace` and `project` together"));
    let list_at = GROK_HANDOFF_RULE_CONTENT
        .find("memory_handoff_list")
        .expect("list tool");
    let accept_at = GROK_HANDOFF_RULE_CONTENT
        .find("memory_handoff_accept")
        .expect("accept tool named so the rule can forbid it as first");
    assert!(
        list_at < accept_at,
        "the rule must mandate list before it mentions accept"
    );
    assert!(
        GROK_HANDOFF_RULE_CONTENT
            .contains("Do not call `memory_handoff_accept` as the first tool")
    );
    assert!(GROK_HANDOFF_RULE_CONTENT.contains("empty `handoffs` array"));
    // A guessed project that does not exist answers with a scope error,
    // not an empty list; the correction must fire on that too.
    assert!(GROK_HANDOFF_RULE_CONTENT.contains("or a scope error"));
    // The baton PostToolUse claimed is authoritative over listed rows.
    assert!(GROK_HANDOFF_RULE_CONTENT.contains("it wins"));
}
