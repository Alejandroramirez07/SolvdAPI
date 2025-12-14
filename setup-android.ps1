# Complete Android setup script
Write-Host "=== Complete Android Test Setup ===" -ForegroundColor Cyan

# 1. Set environment variables permanently
Write-Host "`n1. Setting environment variables..." -ForegroundColor Yellow
$androidPath = "C:\Users\danie\AppData\Local\Android\Sdk"

if (Test-Path $androidPath) {
    # Set for current session
    $env:ANDROID_HOME = $androidPath
    $env:ANDROID_SDK_ROOT = $androidPath
    $env:PATH = "$androidPath\platform-tools;$androidPath\tools;$env:PATH"

    Write-Host "✓ Environment variables set for this session" -ForegroundColor Green

    # Instructions for permanent setup
    Write-Host "`nTo set permanently in Windows:" -ForegroundColor Yellow
    Write-Host "1. Open System Properties" -ForegroundColor Gray
    Write-Host "2. Click 'Environment Variables'" -ForegroundColor Gray
    Write-Host "3. Add new system variables:" -ForegroundColor Gray
    Write-Host "   - ANDROID_HOME = $androidPath" -ForegroundColor Gray
    Write-Host "   - ANDROID_SDK_ROOT = $androidPath" -ForegroundColor Gray
    Write-Host "4. Add to PATH: %ANDROID_HOME%\platform-tools;%ANDROID_HOME%\tools" -ForegroundColor Gray
} else {
    Write-Host " Android SDK not found at: $androidPath" -ForegroundColor Red
}

# 2. Create test configuration
Write-Host "`n2. Creating test configuration..." -ForegroundColor Yellow

# Create carina.properties if needed
$carinaProps = @'
# Carina configuration for Android tests
selenium_url=http://127.0.0.1:4723
url=https://en.wikipedia.org
max_driver_count=1
project.name=Solvd Android Tests
'@

$carinaProps | Out-File "src/test/resources/carina.properties" -Encoding UTF8
Write-Host "✓ Created carina.properties" -ForegroundColor Green

# 3. Create Maven profiles
Write-Host "`n3. Updating Maven configuration..." -ForegroundColor Yellow
Write-Host "Add these profiles to your pom.xml:" -ForegroundColor Gray

$profilesXml = @'
<profiles>
    <profile>
        <id>api-only</id>
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
        <properties>
            <suite.file>src/test/resources/testng_suites/api-tests.xml</suite.file>
        </properties>
    </profile>

    <profile>
        <id>mobile-only</id>
        <properties>
            <suite.file>src/test/resources/testng_suites/mobile-tests.xml</suite.file>
        </properties>
    </profile>

    <profile>
        <id>all-tests</id>
        <properties>
            <suite.file>src/test/resources/testng_suites/all-tests.xml</suite.file>
        </properties>
    </profile>
</profiles>
'@

Write-Host $profilesXml -ForegroundColor Gray

# 4. Test the setup
Write-Host "`n4. Testing Android setup..." -ForegroundColor Yellow

# Check ADB
$adbTest = adb version 2>$null
if ($adbTest) {
    Write-Host "✅ ADB is working:" -ForegroundColor Green
    $adbTest | Select-Object -First 2
} else {
    Write-Host " ADB not found" -ForegroundColor Red
    Write-Host "Make sure platform-tools is in PATH" -ForegroundColor Yellow
}

# List devices
Write-Host "`nConnected Android devices:" -ForegroundColor Yellow
adb devices

Write-Host "`n=== Setup Complete ===" -ForegroundColor Green
Write-Host "Available commands:" -ForegroundColor Cyan
Write-Host "  .\run-tests.ps1 check-android  - Check Android setup" -ForegroundColor Gray
Write-Host "  .\run-tests.ps1 android-setup  - Set Android environment" -ForegroundColor Gray
Write-Host "  .\run-tests.ps1 mobile         - Run mobile tests" -ForegroundColor Gray
Write-Host "  .\run-tests.ps1 api            - Run only API tests" -ForegroundColor Gray
Write-Host "  .\run-tests.ps1 all            - Run all tests" -ForegroundColor Gray