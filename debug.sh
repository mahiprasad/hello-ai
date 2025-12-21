#!/bin/bash
echo "Starting Hello AI in DEBUG mode..."
echo ""
echo "Debug port: 5005"
echo "Application will be available at: http://localhost:8081"
echo ""
echo "To attach debugger:"
echo "  - IntelliJ: Run -> Attach to Process -> Select port 5005"
echo "  - VS Code: Use Java Debug configuration"
echo ""
echo "Press Ctrl+C to stop"
echo ""

mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"

