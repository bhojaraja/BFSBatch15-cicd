# GitHub Actions Workflows

## 📋 Overview

This repository includes three automated GitHub Actions CI/CD workflows for continuous integration, testing, and release management.

## 🔄 Workflows

### 1. Build and Test (`build-and-test.yml`)

**Triggers:**
- Push to `main` or `develop` branches
- Pull requests to `main` or `develop`
- Manual trigger via workflow dispatch

**What it does:**
- ✅ Compiles source code
- ✅ Downloads JUnit/Mockito dependencies
- ✅ Compiles test code
- ✅ Runs 64 JUnit tests
- ✅ Builds executable JAR
- ✅ Runs code quality checks
- ✅ Performs security scans
- ✅ Verifies documentation

**Matrix Testing:**
- Java 11
- Java 17 (LTS)
- Java 21 (Latest)

**Duration:** ~2-3 minutes per Java version

**Status Checks:**
- ✅ Build
- ✅ Code Quality
- ✅ Security
- ✅ Documentation

---

### 2. Test Coverage (`test-coverage.yml`)

**Triggers:**
- Push to `main` or `develop`
- Pull requests to `main` or `develop`
- Manual trigger

**What it does:**
- 📊 Generates detailed test coverage report
- 📈 Reports coverage by component
- 💬 Comments on PRs with coverage details
- 📤 Uploads coverage report as artifact

**Coverage Report Includes:**
- Total tests: 64
- Component breakdown
- Coverage metrics (>80%)
- Test execution time
- Recommendations

**Duration:** ~3-4 minutes

---

### 3. Release Build (`release.yml`)

**Triggers:**
- Git tags matching `v*` (e.g., `v1.0.0`)
- Manual trigger with custom version

**What it does:**
- 📦 Compiles and tests everything
- 🏗️ Creates executable JAR with version info
- 🌐 Creates web UI package (JAR + resources)
- 📝 Generates release notes
- 🚀 Creates GitHub Release
- 📤 Uploads all artifacts

**Artifacts:**
- `calculator-vX.X.X.jar` - Main executable
- `calculator-latest.jar` - Latest version link
- `calculator-webui-vX.X.X.tar.gz` - Web package
- `RELEASE_NOTES.md` - Documentation

**Duration:** ~3-5 minutes

**How to Trigger:**

Option 1 - Git tag:
```bash
git tag v1.0.0
git push origin v1.0.0
```

Option 2 - Manual:
1. Actions tab → Release Build
2. "Run workflow" → Enter version → Run

---

## 📊 Workflow Status

### Build and Test Jobs

```
Matrix Jobs (Parallel):
├── Java 11 Build
├── Java 17 Build
└── Java 21 Build

Sequential Jobs:
├── Code Quality
├── Security
└── Documentation
```

### Expected Results

```
✅ 64 tests passed
✅ No code quality issues
✅ No security vulnerabilities
✅ Documentation complete
✅ JAR builds successfully
```

---

## 🎯 Quick Reference

| Workflow | Trigger | Duration | Artifacts |
|----------|---------|----------|-----------|
| Build & Test | Push/PR | 2-3 min | Test results, JAR |
| Coverage | Push/PR | 3-4 min | Coverage report |
| Release | Tag/Manual | 3-5 min | JAR, Release notes |

---

## 🔧 Configuration

### Change Java Versions

Edit `build-and-test.yml`:
```yaml
strategy:
  matrix:
    java-version: ['11', '17', '21']
```

### Change Trigger Branches

Edit any workflow file:
```yaml
on:
  push:
    branches: [ main, develop, staging ]
```

### Add Scheduled Runs

Add to any workflow:
```yaml
schedule:
  - cron: '0 2 * * *'  # Daily 2 AM UTC
```

---

## 📈 Monitoring

### View Status

1. GitHub repository → **Actions** tab
2. See all workflow runs
3. Click run for details
4. View logs and artifacts

### Get Status Badge

Add to README:
```markdown
![Build and Test](https://github.com/YOUR_ORG/calculator/workflows/Build%20and%20Test/badge.svg)
```

### Notifications

GitHub automatically notifies on:
- ✅ Successful workflows
- ❌ Failed workflows
- ⚠️ Warnings

---

## 🔐 Security

### Best Practices

- ✅ Never commit secrets
- ✅ Use GitHub Secrets for sensitive data
- ✅ Review workflow changes in PRs
- ✅ Limit workflow permissions
- ✅ Use GITHUB_TOKEN with minimal scope

### Add Secrets

1. Settings → Secrets and variables → Actions
2. "New repository secret"
3. Add name and value
4. Reference in workflow: `${{ secrets.NAME }}`

---

## 🐛 Troubleshooting

### Build Fails

1. Check workflow logs
2. Common causes:
   - Java version incompatibility
   - Dependency download failure
   - Test failure

### View Logs

```bash
# List runs
gh run list

# View specific run logs
gh run view <run-id> --log
```

### Local Testing

Run locally before pushing:
```bash
chmod +x build-and-test.sh
./build-and-test.sh
```

---

## 📥 Download Artifacts

### From GitHub

1. Go to Actions tab
2. Click workflow run
3. Scroll to Artifacts section
4. Download desired file

### From CLI

```bash
# List artifacts
gh run list

# Download artifact
gh run download <run-id> -n artifact-name
```

---

## 📊 Performance Metrics

### Typical Times

- Compilation: 10-15s
- Test execution: 5-10s
- Dependency download: 20-30s
- Total per version: ~2-3 min

### Optimization

- Dependencies cached in subsequent runs
- Parallel matrix jobs save time
- All checks run concurrently

---

## 🚀 Deployment Integration

To add automatic deployment:

1. Create deployment workflow
2. Trigger on successful release
3. Deploy JAR to production
4. Example: AWS, DigitalOcean, etc.

---

## 📝 Workflow Files

```
.github/workflows/
├── build-and-test.yml      # Main CI/CD pipeline
├── test-coverage.yml       # Coverage reporting
└── release.yml             # Release builds
```

---

## 🔗 Related Documentation

- [CI_CD_GUIDE.md](../../CI_CD_GUIDE.md) - Detailed CI/CD guide
- [TESTING.md](../../TESTING.md) - Test documentation
- [TEST_GUIDE.md](../../TEST_GUIDE.md) - Detailed test info

---

## ✅ Pre-Flight Checklist

Before committing workflow changes:

- [ ] Workflow syntax is valid
- [ ] Triggers are correct
- [ ] Jobs have descriptive names
- [ ] Steps have clear descriptions
- [ ] Artifacts are uploaded
- [ ] Documentation is updated
- [ ] Secrets are not hardcoded
- [ ] Branch protection is configured

---

## 🎓 GitHub Actions Tips

### Validate Workflows

```bash
gh workflow validate .github/workflows/build-and-test.yml
```

### List Workflows

```bash
gh workflow list
```

### View Workflow Details

```bash
gh workflow view build-and-test.yml
```

### Disable/Enable Workflow

```bash
gh workflow disable build-and-test.yml
gh workflow enable build-and-test.yml
```

---

## 📞 Support

For questions about workflows:

1. Check [GitHub Actions Documentation](https://docs.github.com/actions)
2. Review workflow files in this repo
3. Check logs for error messages
4. Test locally with build scripts

---

**Last Updated:** October 27, 2026  
**Total Workflows:** 3  
**Status:** ✅ Production Ready  
**Framework:** GitHub Actions
