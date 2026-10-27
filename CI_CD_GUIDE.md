# GitHub Actions CI/CD Pipeline Guide

This document describes the GitHub Actions CI/CD pipeline for the Calculator application.

## 📋 Overview

Three automated workflows are configured to ensure code quality and reliability:

1. **Build and Test** - Main pipeline for every push and PR
2. **Test Coverage** - Detailed test coverage reporting
3. **Release Build** - Creates production releases

---

## 🔄 Workflow 1: Build and Test

**File:** `.github/workflows/build-and-test.yml`

### Trigger Events

- **Push** to `main` or `develop` branches
- **Pull requests** to `main` or `develop`
- **Manual trigger** via workflow_dispatch

### What It Does

```
┌─────────────────────┐
│  Checkout Code      │
└──────────┬──────────┘
           ↓
┌─────────────────────┐
│  Setup Java JDK     │  (11, 17, 21)
│  (3 versions)       │
└──────────┬──────────┘
           ↓
┌─────────────────────┐
│  Compile Source     │
└──────────┬──────────┘
           ↓
┌─────────────────────┐
│  Download Deps      │
│  (JUnit, Mockito)   │
└──────────┬──────────┘
           ↓
┌─────────────────────┐
│  Compile Tests      │
└──────────┬──────────┘
           ↓
┌─────────────────────┐
│  Run Tests (64)     │
└──────────┬──────────┘
           ↓
┌─────────────────────┐
│  Build JAR          │
└──────────┬──────────┘
           ↓
┌─────────────────────┐
│  Test JAR           │
└──────────┬──────────┘
           ↓
┌─────────────────────┐
│  Code Quality       │
│  Security Checks    │
│  Documentation      │
└─────────────────────┘
```

### Jobs

#### Main Build Job
- **Name:** `build`
- **Runs on:** `ubuntu-latest`
- **Matrix:** Java 11, 17, 21 (tests on all versions)

**Steps:**
1. Checkout code
2. Setup Java JDK
3. Compile main source
4. Download JUnit/Mockito dependencies
5. Compile tests
6. Run JUnit tests (64 tests)
7. Build executable JAR
8. Verify JAR execution
9. Upload artifacts

**Expected Duration:** 2-3 minutes per Java version

#### Code Quality Job
- **Name:** `code-quality`
- **Checks:**
  - No hardcoded credentials
  - TODO/FIXME comments count
  - Test coverage metrics (>80%)

#### Security Job
- **Name:** `security`
- **Checks:**
  - Command injection risks
  - Path traversal vulnerabilities
  - Dependency vulnerabilities

#### Documentation Job
- **Name:** `documentation`
- **Checks:**
  - Required documentation files exist
  - README presence

### Artifacts

Generated artifacts available for 7-30 days:

- `test-results-java-X.X` - Test execution results
- `calculator-jar-java-X.X` - Compiled JAR files

### How to Access Results

1. Go to **Actions** tab in GitHub
2. Click on workflow run
3. Scroll to **Artifacts** section
4. Download desired artifacts

---

## 📊 Workflow 2: Test Coverage Report

**File:** `.github/workflows/test-coverage.yml`

### Trigger Events

- **Push** to `main` or `develop`
- **Pull requests** to `main` or `develop`
- **Manual trigger**

### What It Does

```
┌─────────────────────┐
│  Compile & Test     │
└──────────┬──────────┘
           ↓
┌─────────────────────┐
│  Generate Report    │
└──────────┬──────────┘
           ↓
┌─────────────────────┐
│  Upload Report      │
└──────────┬──────────┘
           ↓
┌─────────────────────┐
│  Comment on PR      │
│  (if PR)            │
└─────────────────────┘
```

### Coverage Report

The report includes:

- Total tests: 64
- Coverage by component (100% for core logic)
- Coverage metrics table
- Test execution time
- Recommendations

**Sample Output:**
```
| Component | Lines | Covered | Coverage |
|-----------|-------|---------|----------|
| CalculatorEngine.java | 40 | 40 | 100% |
| InputParser.java | 68 | 68 | 100% |
| CalculatorController | 95 | 95 | 100% |
| **Total** | **313** | **283** | **90%** |
```

### PR Comments

When triggered by a PR:
- Automatically comments with coverage report
- Updates on each new commit
- Helps reviewers understand test coverage

---

## 🚀 Workflow 3: Release Build

**File:** `.github/workflows/release.yml`

### Trigger Events

- **Push** with tag matching `v*` (e.g., `v1.0.0`)
- **Manual trigger** with custom version

### What It Does

```
┌──────────────────────┐
│  Compile & Test All  │
└──────────┬───────────┘
           ↓
┌──────────────────────┐
│  Create JAR with     │
│  Version Info        │
└──────────┬───────────┘
           ↓
┌──────────────────────┐
│  Create Web Package  │
│  (JAR + Resources)   │
└──────────┬───────────┘
           ↓
┌──────────────────────┐
│  Generate Release    │
│  Notes               │
└──────────┬───────────┘
           ↓
┌──────────────────────┐
│  Create GitHub       │
│  Release             │
└──────────┬───────────┘
           ↓
┌──────────────────────┐
│  Upload Artifacts    │
└──────────────────────┘
```

### Release Artifacts

Each release includes:

- `calculator-vX.X.X.jar` - Executable JAR
- `calculator-latest.jar` - Latest version
- `calculator-webui-vX.X.X.tar.gz` - Web UI package
- `RELEASE_NOTES.md` - Release documentation

### How to Create a Release

**Method 1: Push a tag**
```bash
git tag v1.0.0
git push origin v1.0.0
```

**Method 2: Manual trigger**
1. Go to **Actions** tab
2. Select "Release Build" workflow
3. Click "Run workflow"
4. Enter version (e.g., v1.0.0)
5. Click "Run workflow"

### Release Process

1. GitHub automatically creates a release
2. JAR files are uploaded
3. Release notes are generated
4. Files available for download on GitHub Releases page

---

## 📈 Monitoring Pipelines

### View Status

1. Go to **Actions** tab
2. View recent workflow runs
3. Click run to see details

### Status Badge

Add to README:
```markdown
![Build and Test](https://github.com/YOUR_ORG/calculator/workflows/Build%20and%20Test/badge.svg)
```

### Notifications

GitHub sends notifications for:
- ✅ Successful workflows
- ❌ Failed workflows
- ⚠️ Warnings

Configure in Settings → Notifications

---

## 🔧 Customization

### Change Java Versions

Edit `.github/workflows/build-and-test.yml`:
```yaml
strategy:
  matrix:
    java-version: ['11', '17', '21']  # Add/remove versions
```

### Change Trigger Branches

Edit workflow files:
```yaml
on:
  push:
    branches: [ main, develop, release ]  # Add branches
```

### Disable a Workflow

1. Go to workflow file
2. Comment out the `on:` section
3. Or delete the workflow file

### Run Workflow on Schedule

Add to workflow:
```yaml
on:
  schedule:
    - cron: '0 2 * * *'  # Daily at 2 AM UTC
```

---

## 🐛 Troubleshooting

### Build Fails

1. Check **Logs** for error messages
2. Common issues:
   - Java version incompatibility
   - Dependency download failures
   - Test failures

### Tests Fail

1. Click on test job logs
2. Look for specific test failure
3. Local reproduction:
   ```bash
   chmod +x build-and-test.sh
   ./build-and-test.sh
   ```

### Artifacts Not Available

- Check retention policy (default: 30 days)
- Old artifacts are automatically deleted
- Create a release to keep artifacts longer

### Dependency Download Fails

- Check internet connectivity in Actions environment
- Verify repository URLs are accessible
- Try re-running workflow

---

## 📊 Performance

### Typical Execution Times

| Workflow | Duration | Status |
|----------|----------|--------|
| Build and Test | 2-3 min | ✅ |
| Test Coverage | 3-4 min | ✅ |
| Release Build | 3-5 min | ✅ |

### Optimization Tips

- Use matrix strategy for parallel Java versions
- Cache dependencies (Maven/Gradle)
- Run quality checks in parallel
- Consider splitting large workflows

---

## 🔐 Security

### Best Practices

✅ Never commit secrets  
✅ Use GitHub Secrets for sensitive data  
✅ Review workflow files in PRs  
✅ Limit workflow permissions  

### Adding Secrets

1. Go to **Settings** → **Secrets and variables** → **Actions**
2. Click **New repository secret**
3. Add secret with value
4. Reference in workflow: `${{ secrets.SECRET_NAME }}`

### Example: GPG Signing

```yaml
- name: Sign Artifacts
  run: gpg --sign calculator.jar
  env:
    GPG_KEY: ${{ secrets.GPG_KEY }}
```

---

## 📚 Examples

### Manually Run Workflow

```bash
# Trigger release build
gh workflow run release.yml -f version=v1.0.0

# View workflow status
gh run list
```

### Download Artifacts Locally

```bash
# List artifacts from latest run
gh run list --workflow build-and-test.yml --limit 1

# Download artifact
gh run download <run-id> -n calculator-jar-java-17
```

### View Workflow Logs

```bash
# View latest run
gh run view --log

# View specific run
gh run view <run-id> --log
```

---

## 🎯 Next Steps

1. **Enable branch protection** - Require passing checks before merge
2. **Add code coverage badge** - Show coverage in README
3. **Setup deployment** - Automatically deploy on release
4. **Add notifications** - Slack/Teams on failures
5. **Archive releases** - Store production releases

---

## 📞 Support

For issues with CI/CD:

1. Check GitHub Actions documentation: https://docs.github.com/actions
2. Review workflow logs for error messages
3. Test locally with provided build scripts
4. Check workflow syntax: `gh workflow validate`

---

## 📝 Workflow Status Checks

### Required Checks for PR

Configure in **Settings** → **Branches** → **Add branch protection rule**

Require passing:
- ✅ Build and Test (all Java versions)
- ✅ Code Quality
- ✅ Security
- ✅ Test Coverage (optional)

### PR Status

Before merge, PR must show:
```
✅ All checks passed
  - build / build (ubuntu-latest) — passed
  - code-quality / code-quality — passed
  - security / security — passed
```

---

## 🎓 CI/CD Best Practices

1. **Fail Fast** - Run quick checks first
2. **Parallel Jobs** - Run independent jobs simultaneously
3. **Clear Logging** - Output meaningful messages
4. **Artifact Management** - Clean up old artifacts
5. **Documentation** - Keep workflows documented
6. **Testing** - Comprehensive test coverage
7. **Security** - Regular dependency updates
8. **Automation** - Minimize manual steps

---

**Last Updated:** October 27, 2026  
**CI/CD Framework:** GitHub Actions  
**Status:** ✅ Production Ready
