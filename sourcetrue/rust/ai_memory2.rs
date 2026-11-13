fn parse_frontmatter(skill: &ManagedSkill) -> Frontmatter {
    let Some(rest) = skill.content.strip_prefix("---\n") else {
        panic!("{} must start with frontmatter", skill.name);
    };
    let Some((frontmatter, _body)) = rest.split_once("\n---\n") else {
        panic!("{} must close frontmatter", skill.name);
    };
    serde_saphyr::from_str(frontmatter)
        .unwrap_or_else(|e| panic!("{} frontmatter must be valid YAML: {e}", skill.name))
}

fn directory_name(skill: &ManagedSkill) -> &str {
    skill
        .relative_path
        .split('/')
        .next()
        .unwrap_or_else(|| panic!("{} has an empty relative path", skill.name))
}