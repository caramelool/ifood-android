const THRESHOLD = 70;

function progressBar(pct) {
  const filled = Math.round(pct / 5);
  return "█".repeat(filled) + "░".repeat(20 - filled) + ` ${pct}%`;
}

function buildBody(buildType, inputs) {
  if (inputs.available !== "true") {
    return `## ⚠️ Coverage Report — ${buildType}\n\nNo coverage data found. The report may not have been generated.`;
  }

  const linePct   = parseFloat(inputs.linePct)   || 0;
  const branchPct = parseFloat(inputs.branchPct) || 0;
  const instrPct  = parseFloat(inputs.instrPct)  || 0;
  const passed    = linePct >= THRESHOLD;
  const icon      = passed ? "✅" : "❌";
  const result    = passed
    ? `Passed (${linePct}%)`
    : `Failed — ${linePct}% is below the ${THRESHOLD}% threshold`;

  return `## ${icon} Coverage Report — ${buildType}

| Metric | Covered | Total | Coverage |
|--------|--------:|------:|----------|
| Lines | ${inputs.lineCovered} | ${inputs.lineTotal} | \`${progressBar(linePct)}\` |
| Branches | ${inputs.branchCovered} | ${inputs.branchTotal} | \`${progressBar(branchPct)}\` |
| Instructions | ${inputs.instrCovered} | ${inputs.instrTotal} | \`${progressBar(instrPct)}\` |

**Minimum required:** ${THRESHOLD}% &nbsp;•&nbsp; **Result:** ${result}`;
}

module.exports = async ({ github, context, inputs }) => {
  const buildType = inputs.buildType;
  const marker    = `<!-- kover-coverage-${buildType.toLowerCase()} -->`;
  const body      = buildBody(buildType, inputs);
  const fullBody  = `${marker}\n${body}`;

  const { data: comments } = await github.rest.issues.listComments({
    owner: context.repo.owner,
    repo: context.repo.repo,
    issue_number: context.issue.number,
  });

  const existing = comments.find((c) => c.body.includes(marker));
  if (existing) {
    await github.rest.issues.updateComment({
      owner: context.repo.owner,
      repo: context.repo.repo,
      comment_id: existing.id,
      body: fullBody,
    });
  } else {
    await github.rest.issues.createComment({
      owner: context.repo.owner,
      repo: context.repo.repo,
      issue_number: context.issue.number,
      body: fullBody,
    });
  }
};
