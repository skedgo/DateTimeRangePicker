# Security Policy

## Supported Versions

We release patches for security vulnerabilities in the following versions:

| Version | Supported          |
| ------- | ------------------ |
| 1.x.x   | :white_check_mark: |
| < 1.0   | :x:                |

> **Note:** Please update this table to reflect the actual versions you support.

---

## Reporting a Vulnerability

We take the security of our software seriously. If you believe you have found a security vulnerability, please report it to us as described below.

### How to Report

**Please do not report security vulnerabilities through public GitHub issues.**

Instead, please email us at **[security@skedgo.com](mailto:security@skedgo.com)**.

You should receive a response within 48 hours. If for some reason you do not, please follow up via email to ensure we received your original message.

### What to Include

Please include the following information in your report:

- Type of issue (e.g., buffer overflow, SQL injection, cross-site scripting, etc.)
- Full paths of source file(s) related to the manifestation of the issue
- The location of the affected source code (tag/branch/commit or direct URL)
- Any special configuration required to reproduce the issue
- Step-by-step instructions to reproduce the issue
- Proof-of-concept or exploit code (if possible)
- Impact of the issue, including how an attacker might exploit it

This information will help us triage your report more quickly.

### What to Expect

After you submit a report, we will:

1. **Acknowledge** your email within 48 hours
2. **Investigate** the issue and confirm the vulnerability
3. **Keep you informed** of our progress toward a fix
4. **Release** a security patch as appropriate
5. **Credit** you in our release notes (if you wish to be named)

---

## Security Best Practices for Contributors

If you're contributing to this project, please follow these security guidelines:

### Code Review
- All code changes must go through Pull Requests
- PRs require approval from at least one maintainer before merging
- No direct commits to `main` or protected branches

### Dependencies
- Keep dependencies up to date
- Review dependency changes for known vulnerabilities
- Use automated tools like Dependabot to monitor security issues

### Secrets Management
- **Never** commit credentials, API keys, tokens, or other secrets
- Use environment variables or secure secret management systems
- Review commits for accidentally included secrets before pushing

### Secure Coding
- Follow [OWASP Top 10](https://owasp.org/www-project-top-ten/) best practices
- Validate and sanitize all user inputs
- Use parameterized queries to prevent SQL injection
- Implement proper authentication and authorization
- Use HTTPS/TLS for all network communications

---

## Security Features

This project includes the following security measures:

- **Dependabot alerts** enabled for vulnerable dependencies
- **Secret scanning** enabled to prevent credential leaks
- **Code review** required for all changes
- **Branch protection** rules enforced on main branches

---

## Disclosure Policy

We follow a **coordinated disclosure** approach:

1. Security issues are privately investigated and patched
2. A security advisory is prepared but not published
3. We notify relevant parties (e.g., major users, downstream projects)
4. A patch release is made available
5. The security advisory is published after users have had time to update

We aim to complete this process within 90 days of the initial report, though complex issues may take longer.

---

## Security Update Policy

Security updates are released as:
- **Patch versions** (x.x.X) for currently supported versions
- **Security advisories** published on our GitHub Security Advisories page
- **Release notes** clearly marking security-related changes

---

## Additional Resources

- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [CWE Top 25 Most Dangerous Software Weaknesses](https://cwe.mitre.org/top25/)
- [GitHub Security Best Practices](https://docs.github.com/en/code-security)

---

## Contact

For general security questions or concerns, please contact:
- **Email:** [security@skedgo.com](mailto:security@skedgo.com)

---

> **Last Updated:** {{ DATE }}  
> **Version:** 1.0  
> 
> This security policy is maintained by the repository maintainers and reviewed regularly.
