param([string]$Command="help")

function Show-Help {
    Write-Host "=== Solvd Test Runner ==="
    Write-Host ""
    Write-Host "Available commands:"
    Write-Host "  build          - Build Docker image"
    Write-Host "  test           - Run tests in Docker"
    Write-Host "  local          - Run tests locally"
    Write-Host "  api            - Run only API tests"
    Write-Host "  mobile         - Run mobile tests"
    Write-Host "  all            - Run all tests"
    Write-Host "  infra-start    - Start Selenium+Appium"
    Write-Host "  infra-stop     - Stop infrastructure"
    Write-Host "  infra-status   - Check infrastructure"
    Write-Host "  clean          - Clean Docker"
    Write-Host "  help           - Show this help"
}

function Build-Docker {
    Write-Host "Building Docker image..."
    docker build -t solvd-tests .
}

function Run-Tests-Docker {
    docker-compose up --build test-runner
    docker-compose down
}

function Run-Tests-Local {
    mvn clean test
}

function Run-API-Tests {
    mvn clean test -Papi-only
}

function Run-Mobile-Tests {
    mvn clean test -Pmobile-only
}

function Run-All-Tests {
    mvn clean test -Pall-tests
}

function Start-Infrastructure {
    docker-compose up -d selenium-hub selenium-chrome appium
    Write-Host "Infrastructure started:"
    Write-Host "  Selenium: http://localhost:4444"
    Write-Host "  Appium: http://localhost:4723"
}

function Stop-Infrastructure {
    docker-compose down
    Write-Host "Infrastructure stopped"
}

function Show-Infrastructure-Status {
    docker-compose ps
}

function Clean-Docker {
    docker system prune -f
    Write-Host "Docker cleanup done"
}

switch ($Command.ToLower()) {
    "build"         { Build-Docker }
    "test"          { Run-Tests-Docker }
    "local"         { Run-Tests-Local }
    "api"           { Run-API-Tests }
    "mobile"        { Run-Mobile-Tests }
    "all"           { Run-All-Tests }
    "infra-start"   { Start-Infrastructure }
    "infra-stop"    { Stop-Infrastructure }
    "infra-status"  { Show-Infrastructure-Status }
    "clean"         { Clean-Docker }
    "help"          { Show-Help }
    default         { Show-Help }
}