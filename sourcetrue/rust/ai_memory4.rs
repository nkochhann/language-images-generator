impl std::str::FromStr for HandoffState {
    type Err = crate::MemoryError;

    fn from_str(s: &str) -> Result<Self, Self::Err> {
        match s {
            "open" => Ok(Self::Open),
            "accepted" => Ok(Self::Accepted),
            "expired" => Ok(Self::Expired),
            other => Err(crate::MemoryError::MalformedRecord(format!(
                "unknown handoff state: {other}"
            ))),
        }
    }
}

/// Input for inserting a new handoff.
#[derive(Debug, Clone, Serialize, Deserialize)]
pub struct NewHandoff {
    /// Owning workspace.
    pub workspace_id: WorkspaceId,
    /// Owning project.
    pub project_id: ProjectId,
    /// Session this handoff captures (None for manual handoffs).
    pub from_session_id: Option<SessionId>,
    /// Agent CLI that produced this handoff.
    pub from_agent: AgentKind,
    /// Optional explicit target hint (`claude-code`, `codex`, …).
    pub to_agent: Option<AgentKind>,
    /// Working directory at handoff time. Used to match the next
    /// session's `memory_handoff_accept` call.
    pub cwd: Option<PathBuf>,
    /// One-paragraph summary of where we left off.
    pub summary: String,
    /// Open questions for the next agent.
    pub open_questions: Vec<String>,
    /// Suggested next steps.
    pub next_steps: Vec<String>,
    /// Files touched in the session.
    pub files_touched: Vec<String>,
    /// Operator this handoff belongs to, as an
    /// [`crate::IdentityKey::storage_key`] string. `None` publishes it to the
    /// whole project (the pre-ownership behaviour, and what a caller with no
    /// actor produces).
    #[serde(default)]
    pub owner_user: Option<String>,
}