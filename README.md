# 🚍 Transport Management System

[![Java](https://img.shields.io/badge/Java-8+-blue.svg)](https://www.oracle.com/java/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)]()

> A comprehensive Java-based application for managing bus registrations with secure authentication and role-based access control. 🛡️

## 📋 Table of Contents

- [🎯 Overview](#-overview)
- [✨ Features](#-features)
- [🏗️ Architecture](#️-architecture)
- [📦 Installation](#-installation)
- [🚀 Usage](#-usage)
- [🔒 Security Considerations](#-security-considerations)
- [🔧 Build and Deployment](#-build-and-deployment)
- [📁 File Structure Details](#-file-structure-details)
- [🐛 Troubleshooting](#-troubleshooting)
- [🤝 Contributing](#-contributing)
- [📄 License](#-license)
- [🔮 Future Enhancements](#-future-enhancements)
- [📞 Support](#-support)

## 🎯 Overview

The **Transport Management System** is a comprehensive Java-based application designed for managing bus registrations within a university or organizational setting. 🎓🏢

It provides a beautiful graphical user interface (GUI) for administrators, drivers, and users (students/staff/faculty members) to interact with the system, facilitating the registration process for bus routes, generating vouchers, and managing system data.

### 🎨 Key Highlights
- 🔐 **Role-based access control** with separate dashboards for Admin, Driver, and User (Student/Staff/Faculty) roles
- 🛡️ **Secure authentication** using password hashing with salts
- 💾 **Persistent data storage** through Java serialization
- 📊 **Real-time statistics** and monitoring
- 🎫 **Automated voucher generation**
- 📝 **Comprehensive logging** for audit trails

## ✨ Features

### 👥 User Roles and Dashboards

#### 👨‍💼 Administrator Dashboard
- 📈 View system statistics (total revenue, pending/approved registrations)
- 🗺️ Monitor route-specific registrations
- ✅ Approve or reject user registrations
- 📋 Access comprehensive registration data
- 🔍 Advanced filtering and search capabilities

#### 🚗 Driver Dashboard
- 🛣️ View assigned routes and schedules
- 👥 Access passenger lists for their routes
- 📅 Daily schedule management
- 📍 Real-time route tracking (simulated)

#### 👤 User Dashboard (Students/Staff/Faculty)
- 🚌 Register for available bus routes
- 📚 View registration history and status
- 📄 Download and print registration vouchers
- 👤 Manage personal profile and preferences
- 🔔 Receive notifications about route changes or approvals

### 🔧 Core Functionality

| Feature | Description | Status |
|---------|-------------|--------|
| 🚌 **Bus Route Management** | Predefined routes with stops, timings, and capacity | ✅ Active |
| 📝 **User Registration** | Multi-step registration process with validation | ✅ Active |
| 🎫 **Voucher Generation** | Automatic generation of registration vouchers (text-based PDFs) | ✅ Active |
| 💾 **Data Persistence** | All data stored in serialized files for persistence | ✅ Active |
| 📊 **Logging** | Comprehensive logging of system activities | ✅ Active |
| 🔐 **Secure Authentication** | Password hashing with salt for secure storage | ✅ Active |

### 🛡️ Security Features
- 🔒 **Password Hashing**: Uses SHA-256 hashing with randomly generated salts
- 👥 **Role-Based Access**: Different permissions for Admin, Driver, and User roles
- ✅ **Input Validation**: Form validation to prevent invalid data entry
- 🔐 **Data Encryption**: Sensitive data stored in hashed format
- 📝 **Audit Logging**: All actions logged for security monitoring

## 🏗️ Architecture

### 📂 Project Structure
```
src/
├── busregistrationsystem/
│   ├── Main.java                    🚀 # Application entry point
│   ├── data/
│   │   └── DataManager.java         📊 # Singleton data management class
│   ├── gui/                         🎨 # Graphical user interface components
│   │   ├── StartPage.java           🏠 # Main login screen
│   │   ├── AdminDashboard.java      👨‍💼 # Administrator interface
│   │   ├── DriverDashboard.java     🚗 # Driver interface
│   │   ├── UserDashboard.java       👤 # User interface
│   │   ├── RegistrationForm.java    📝 # User registration form
│   │   ├── RouteDisplay.java        🗺️ # Route information display
│   │   └── RoundButton.java         🔘 # Custom UI component
│   ├── model/                       🏛️ # Data model classes
│   │   ├── Person.java              👤 # Abstract base class for users
│   │   ├── Admin.java               👨‍💼 # Administrator model
│   │   ├── Driver.java              🚗 # Driver model
│   │   ├── User.java                👤 # User model
│   │   ├── BusRoute.java            🚌 # Bus route model
│   │   ├── Registration.java        📝 # Registration model
│   │   └── Client.java              👥 # Client model (if applicable)
│   └── util/                        🛠️ # Utility classes
│       ├── PasswordUtil.java        🔐 # Password hashing utilities
│       ├── PDFGenerator.java        📄 # Voucher generation
│       └── LogUtil.java             📊 # Logging utilities
data/                                💾 # Serialized data files
logs/                                📝 # Application log files
vouchers/                            🎫 # Generated registration vouchers
```

### 🏛️ Design Patterns
- 🔸 **Singleton Pattern**: DataManager ensures single instance for data access
- 🔸 **MVC Pattern**: Separation of model (data), view (GUI), and controller (data management) logic
- 🔸 **Factory Pattern**: Object creation through centralized DataManager

### 💾 Data Storage
- 📁 Uses Java serialization for object persistence
- 📂 Separate files for different data types (users.dat, admins.dat, drivers.dat, etc.)
- 🔄 Automatic data loading on application startup
- 💡 **Note**: Consider database migration for production scalability

## 📦 Installation

### ✅ Prerequisites
- ☕ **Java Development Kit (JDK)** 8 or higher
- 💻 **IntelliJ IDEA** or any Java IDE (recommended for project files)
- 💾 **Minimum 100MB** free disk space

### 🚀 Setup Steps
1. 📥 **Clone or download** the project files
2. 📂 **Open the project** in your Java IDE
3. 🔍 **Verify project structure** matches the provided layout
4. ⚙️ **Compile the Java source files**
5. ▶️ **Run Main.java** to start the application

### 📚 Dependencies
The application uses only **standard Java libraries** - no external dependencies required! 🎉

| Library | Purpose | Version |
|---------|---------|---------|
| Java Swing | GUI components | JDK 8+ |
| Java I/O | File operations | JDK 8+ |
| Java Security | Password hashing | JDK 8+ |
| Java Logging | System logging | JDK 8+ |

## 🚀 Usage

### ▶️ Running the Application
```bash
# Compile the project
javac -d bin src/busregistrationsystem/**/*.java

# Run the application
java -cp bin busregistrationsystem.Main
```

### 🎮 Quick Start Demo
1. 🚀 Launch the application
2. 🏠 The StartPage window appears with login options
3. 👥 Select your role:
   - 👨‍💼 **Admin**: Use default credentials
   - 🚗 **Driver**: Use default credentials  
   - 👤 **User**: Register as new student/staff/faculty member
4. 🔑 Enter credentials (see default credentials below)
5. 🎯 Start using the system!

### 🔑 Default Credentials

> ⚠️ **CRITICAL SECURITY WARNING**: Change these immediately in production!

| Role | Username | Password | Access Level | Notes |
|------|----------|----------|--------------|-------|
| 👨‍💼 **Administrator** | `admin` | `admin123` | Full System Access | Pre-configured account |
| 🚗 **Driver** | `driver1` | `driver123` | Route Management | Pre-configured account |
| 👤 **User** (Students/Staff/Faculty) | - | - | Registration Access | Must register via the system |

### 👤 User Workflow (Students/Staff/Faculty)
1. 🔐 **Login** → Select "User" role and enter credentials (or register if new)
2. 📝 **Register Account** → Create account with university details (for new users)
3. 🚌 **Select Route** → Choose preferred bus route from available options
4. 📋 **Submit Registration** → Fill out registration form with personal details
5. ⏳ **Wait for Approval** → Admin reviews and approves registration
6. ✅ **Get Approved** → Receive approval notification
7. 🎫 **Download Voucher** → Get your bus pass for travel!

### 👨‍💼 Administrator Functions
- 📊 Monitor system statistics and KPIs
- ✅ Approve/reject pending registrations
- 📋 View detailed registration reports
- 🔍 Search and filter registration data
- 📈 Generate revenue and usage reports

### 🚗 Driver Functions
- 🗺️ View assigned routes and schedules
- 👥 Access passenger manifests
- 📍 Track route progress
- 📞 Contact support for issues

## 🔒 Security Considerations

### 🔐 Password Security
- 🛡️ **SHA-256 Hashing** with randomly generated salts
- 🚫 **Never stores** plain text passwords
- 🎲 **Unique salts** for each user account
- ✅ **Secure verification** process

### 👥 Authentication & Authorization
- 🔒 **Role-Based Access Control** (RBAC)
- 🚪 **Session Management** through GUI state
- 🛡️ **Input Sanitization** and validation
- 📝 **Audit Trails** for all actions

### 💾 Data Protection
- 📁 **Serialized Object Storage** with access controls
- 💽 **File-Based Persistence** (upgrade to database recommended)
- 📊 **Comprehensive Logging** for security monitoring
- 🔄 **Regular Backups** recommended

### 🛡️ Security Best Practices
1. 🔄 **Change default passwords** immediately after setup
2. 🔑 **Use strong, unique passwords** for all accounts
3. 💾 **Regular data backups** to prevent data loss
4. 👀 **Monitor log files** for suspicious activity
5. 🚫 **Implement account lockout** after failed attempts
6. 🗄️ **Migrate to secure database** for production use

### ⚠️ Known Security Limitations
- 🚫 File-based storage not suitable for concurrent multi-user access
- ❌ No password complexity requirements enforced
- ⏰ No automatic session timeout implemented
- 🔓 Default credentials pose significant security risk

## 🔧 Build and Deployment

### ⚙️ Compilation
```bash
# Create output directory
mkdir -p bin

# Compile all Java files
javac -d bin -cp src src/busregistrationsystem/**/*.java

# Alternative: Compile with classpath
javac -d bin -sourcepath src src/busregistrationsystem/Main.java
```

### ▶️ Execution
```bash
# Run the compiled application
java -cp bin busregistrationsystem.Main

# With additional JVM options
java -Xmx512m -cp bin busregistrationsystem.Main
```

### 💻 IDE Setup
- 📂 **Import as IntelliJ IDEA project** using the .iml files
- ▶️ **Set Main.java as run configuration**
- 🔧 **Configure JDK 8+** in project settings
- 📦 **Add src folder to classpath**

### 📦 Packaging
```bash
# Create JAR file (optional)
jar cfe TransportSystem.jar busregistrationsystem.Main -C bin .

# Run JAR
java -jar TransportSystem.jar
```

## 📁 File Structure Details

### 💾 Data Files
| File | Purpose | Location |
|------|---------|----------|
| `users.dat` | 👥 Serialized user objects | `data/` |
| `admins.dat` | 👨‍💼 Serialized administrator objects | `data/` |
| `drivers.dat` | 🚗 Serialized driver objects | `data/` |
| `routes.dat` | 🚌 Serialized bus route objects | `data/` |
| `registrations.dat` | 📝 Serialized registration objects | `data/` |

### 📊 Log Files
- 📝 `logs/application.log`: System activity logs with timestamps
- 🔍 Contains INFO, WARNING, and ERROR level messages
- 📅 Automatic log rotation (configurable)

### 🎫 Voucher Files
- 📄 `vouchers/voucher_[id].txt`: Generated registration vouchers
- 📋 Contains registration details, route info, and validity
- 🖨️ Text-based format (upgrade to PDF recommended)

## 🐛 Troubleshooting

### 🚨 Common Issues & Solutions

| Issue | Symptom | Solution |
|-------|---------|----------|
| 📁 **Data Loading Error** | App crashes on startup | ✅ Ensure `data/` directory exists and files aren't corrupted |
| 🖥️ **GUI Display Issues** | Windows don't render properly | ✅ Verify Java Swing support and graphics drivers |
| 🔒 **File Permission Error** | Can't write to directories | ✅ Grant write permissions to `data/`, `logs/`, `vouchers/` |
| 🔐 **Login Failure** | Invalid credentials error | ✅ Check username/password, ensure correct role selected |
| 💾 **Out of Memory** | Application freezes | ✅ Increase JVM heap size: `java -Xmx1g -cp bin Main` |

### 📊 Logs & Debugging
```bash
# Check application logs
tail -f logs/application.log

# Enable debug logging (if implemented)
java -Djava.util.logging.level=FINE -cp bin busregistrationsystem.Main
```

### 🆘 Getting Help
- 📖 Check this README for common solutions
- 🔍 Search existing issues in the repository
- 📧 Contact the development team
- 🐛 Report bugs with detailed steps to reproduce

## 🤝 Contributing

We welcome contributions! 🎉 Here's how you can help:

### 📋 Contribution Process
1. 🍴 **Fork** the repository
2. 🌿 **Create a feature branch**: `git checkout -b feature/amazing-feature`
3. 💻 **Make changes** following our coding standards
4. 📝 **Add appropriate logging** and error handling
5. 🧪 **Test thoroughly** - run the application and verify functionality
6. 📤 **Submit a pull request** with a clear description

### 🎨 Code Style Guidelines
- 📝 **Meaningful names**: Use descriptive variable and method names
- 💬 **Comments**: Add comments for complex logic and algorithms
- 📏 **Java Conventions**: Follow standard Java naming conventions
- 🛡️ **Exception Handling**: Implement proper try-catch blocks
- 🔧 **Code Formatting**: Consistent indentation and spacing

### 🧪 Testing
- ✅ **Manual Testing**: Test all user roles and workflows
- 🔍 **Edge Cases**: Test with invalid inputs and error conditions
- 📊 **Data Integrity**: Verify data persistence across sessions
- 🖥️ **Cross-Platform**: Test on different operating systems

## 📄 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## 🔮 Future Enhancements

### 🚀 Planned Features
- 🗄️ **Database Integration** (PostgreSQL/MySQL) for better concurrency
- 📄 **PDF Generation** using iText or Apache PDFBox libraries
- 📧 **Email Notifications** for registration confirmations and updates
- 📱 **Mobile App Companion** for Android/iOS
- 📊 **Advanced Analytics** and reporting dashboard
- 🌍 **Multi-language Support** (i18n)
- 🤖 **Automated Testing Suite** with JUnit

### 💡 Community Suggestions
- 🔄 **Real-time Updates** using WebSocket connections
- 📍 **GPS Integration** for live bus tracking
- 💳 **Payment Integration** for online fee collection
- 📱 **QR Code Vouchers** for easy validation
- 🤝 **API Endpoints** for third-party integrations

## 👤 Author

**Muhammad Omer**

- GitHub: [@PROCODER-STAR](https://github.com/PROCODER-STAR)
- Email: muhammadomer17806@gmail.com

### 🆘 Need Help?
1. 📖 **Read the Documentation**: Start with this README
2. 🔍 **Search Existing Issues**: Check if your problem was already reported
3. 🐛 **Report Bugs**: Use the issue template with detailed information
4. 💡 **Feature Requests**: Describe your idea clearly with use cases

### 🤝 Community
- 🌟 **Star the repository** if you find it useful!
- 🍴 **Fork and contribute** improvements
- 📣 **Share feedback** to help us improve
- 👥 **Join discussions** for questions and suggestions

---

<div align="center">

**Made with ❤️ for efficient transportation management**

⭐ **Star this repo** if you found it helpful!
