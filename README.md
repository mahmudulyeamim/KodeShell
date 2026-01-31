# KodeShell 🚀

<div align="center">
  <h3>Your All-in-One Competitive Programming Companion</h3>
  <p>A comprehensive Android mobile application that unifies contest management, progress tracking, and community engagement across multiple competitive programming platforms.</p>
</div>

---

## 📖 Table of Contents

- [Overview](#-overview)
- [Key Features](#-key-features)
- [Platform Integrations](#-platform-integrations)
- [Technical Architecture](#-technical-architecture)
- [Features Breakdown](#-features-breakdown)
- [Technologies Used](#-technologies-used)
- [Installation](#-installation)
- [Project Structure](#-project-structure)
- [Screenshots](#-screenshots)
- [Future Enhancements](#-future-enhancements)
- [Contributing](#-contributing)
- [License](#-license)

---

## 🎯 Overview

**KodeShell** is an Android mobile application originally developed for the CSE-2216 Application Development Lab. It addresses a common pain point for competitive programmers: the need to navigate multiple websites to track contests, submissions, and connect with the community.

### Problem Statement
Competitive programmers typically juggle between Codeforces, LeetCode, AtCoder, and other platforms to:
- Track upcoming contests
- Monitor submission history
- View profile statistics
- Connect with other programmers

### Solution
KodeShell provides a **unified dashboard** that aggregates all this information in one place, plus adds social networking features specifically designed for the competitive programming community.

---

## ✨ Key Features

### 🏆 Contest Management
- **Unified Contest Calendar**: View upcoming contests from Codeforces, LeetCode, and AtCoder in a single feed
- **Smart Reminders**: Set custom alarms for contests you want to participate in
- **Instant Notifications**: Get notified before contests begin
- **Contest Details**: Direct access to contest links, duration, and timing information
- **Contest Insider**: Detailed view of each contest with registration links

### 👤 Multi-Platform Profile Integration
- **Codeforces Integration**: View user info, rating, rank, and last 10 submissions
- **LeetCode Integration**: Display solve statistics, contest rankings, and problem-solving history
- **AtCoder Integration**: Track contest participation and performance metrics
- **Unified Profile View**: See all your competitive programming stats in one place with tabbed interface
- **Profile Linking**: Connect your handles from all three platforms

### 📱 Social & Community Features
- **News Feed**: Community-driven content with post creation and sharing
- **Real-time Messaging**: One-on-one chat with other programmers to discuss problems
- **Comment System**: Engage in discussions on posts with threaded comments
- **Upvote/Downvote**: Reddit-style voting system for posts
- **User Discovery**: Search for programmers by their platform handles
- **Stalk Feature**: View other users' complete profiles and submission history across all platforms
- **User Directory**: Browse all registered users on KodeShell

### 🔐 Authentication & User Management
- **Email/Password Authentication**: Traditional signup and login
- **Google OAuth**: Quick sign-in with Google account
- **Two-Step Registration**: Comprehensive profile setup (personal info + platform handles)
- **OTP Verification**: Phone number verification for enhanced security
- **Password Recovery**: Forgot password with email-based reset
- **Profile Customization**: Choose from 12 different avatar options
- **Edit Profile**: Update personal information, status message, and linked platform handles

### 🔔 Notification System
- **Contest Reminders**: Custom alarm system using Android AlarmManager
- **Exact Timing**: Schedule notifications with precise timing before contests
- **Wake Lock**: Ensures notifications work even when device is asleep
- **Push Notifications**: Real-time updates for messages and community activities

### 📊 Submission Tracking
- **Recent Submissions**: View your last 10 Codeforces submissions with verdict and problem details
- **Submission History**: Track problem-solving progress across platforms
- **Detailed View**: See submission time, language, verdict, and problem information
- **Time Parsing**: Local time zone conversion for accurate submission timestamps

---

## 🌐 Platform Integrations

### Codeforces API
- **Implementation**: `CodeforcesAPIHelper.java`
- **API Library**: Android Volley
- **Features Integrated**:
  - User information (rating, rank, contribution)
  - Submission history (last N submissions)
  - Upcoming contests list
  - Contest details

### LeetCode GraphQL API
- **Implementation**: `LeetcodeAPIHelper.java`
- **API Library**: OkHttp3
- **Features Integrated**:
  - User profile statistics
  - Problem-solving progress
  - Contest rankings
  - Submission counts by difficulty

### AtCoder API
- **Implementation**: `AtcoderAPIHelper.java`
- **API Library**: OkHttp3
- **Data Source**: Kontests.net API for contest schedules
- **Features Integrated**:
  - Contest calendar
  - User statistics
  - Participation history

---

## 🏗️ Technical Architecture

### Architecture Pattern
- **MVC (Model-View-Controller)**: Traditional Android architecture
- **Fragment-based Navigation**: Bottom navigation with 5 main sections
- **ViewPager**: Tabbed interfaces for profile views

### Backend Services
- **Firebase Realtime Database**: 
  - User profiles storage
  - Posts and comments
  - Real-time messaging
  - Contest reminders
- **Firebase Authentication**: User auth management
- **Firebase Storage**: Avatar and media storage (if implemented)

### Networking Layer
- **Volley**: HTTP requests for Codeforces API
- **OkHttp3**: Advanced HTTP client for LeetCode and AtCoder
- **JSON Parsing**: Native Android JSON handling

### UI/UX Components
- **Material Design**: Material Toolbar, BottomNavigationView, TextInputLayout
- **RecyclerView**: Efficient list rendering for posts, contests, messages
- **CardView**: Modern card-based UI elements
- **ViewPager2**: Smooth tab navigation in profiles
- **SwipeRefreshLayout**: Pull-to-refresh functionality
- **CircleImageView**: Circular profile avatars
- **DrawerLayout**: Navigation drawer for profile section

---

## 📦 Features Breakdown

### 1. Authentication Flow
**Activities**: `LoginActivity`, `SignupActivity1`, `SignupActivity2`, `OtpActivity`, `ForgotPasswordActivity`, `ResetPasswordActivity`

- Two-step registration process:
  1. **Step 1**: Personal information (name, email, password, phone)
  2. **Step 2**: Platform handles (Codeforces, LeetCode, AtCoder)
- Phone number verification via OTP
- Password reset via email
- Google OAuth integration
- Session management with Firebase Auth

### 2. Main Navigation
**Activity**: `MainActivity`
**Bottom Navigation Sections**:

1. **Home** (`HomeFragment`)
   - Social feed with posts
   - Quick access to messaging
   - Search users functionality
   - Create new posts

2. **News** (`NewsFragment`)
   - Community news and announcements
   - News cards with details view
   - Pull-to-refresh for latest updates

3. **Contests** (`ContestFragment`)
   - Aggregated contest calendar
   - Multi-platform contest listings
   - Filter and search options
   - Set reminders for contests

4. **Profile** (`ProfileFragment`)
   - Personal profile overview
   - Tabbed view: KodeShell, Codeforces, LeetCode, AtCoder
   - Edit profile option
   - Navigation drawer menu
   - About Us section

5. **Stalk** (`StalkFragment`)
   - Search users by platform handles
   - View other users' profiles
   - See their submission history across all platforms
   - Access their KodeShell activity

### 3. Profile System
**Fragments**: `ProfileKodeShellFragment`, `ProfileCodeForcesFragment`, `ProfileLeetCodeFragment`, `ProfileAtCoderFragment`

**Profile Components**:
- **KodeShell Tab**: 
  - User's posts
  - Contribution score
  - Activity metrics
  - Status message
  
- **Codeforces Tab**: 
  - User rating and rank
  - Color-coded rating badge
  - Last 10 submissions with verdicts
  - Problem links
  
- **LeetCode Tab**: 
  - Total problems solved
  - Easy/Medium/Hard breakdown
  - Contest ranking
  - Acceptance rate
  
- **AtCoder Tab**: 
  - Contest participation
  - Performance metrics
  - Rank and rating

**Edit Profile** (`EditProfileFragment`):
- Change avatar (12 options)
- Update name, email, phone
- Modify status message
- Link/update platform handles

### 4. Contest Management
**Activities**: `ContestInsiderActivity`, `ContestReminder`, `Upcoming_Codeforces_List`
**Service**: `AlarmReceiver`

- Fetch contests from multiple platforms simultaneously
- Display contests with:
  - Platform logo
  - Contest name
  - Start time (local timezone)
  - Duration
  - Registration link
- Set reminders with custom time offset
- Notification system with exact alarm scheduling
- WebView for direct contest access

### 5. Social Features
**Activities/Fragments**: `ChatActivity`, `NewPostFragment`, `CommentFragment`, `UserList`

**Posts System**:
- Create posts with text content
- Upvote/downvote mechanism
- View count tracking
- Comment on posts
- Delete own posts
- Share with community

**Messaging**:
- Real-time one-on-one chat
- Message history persistence
- Typing indicators (potential)
- User online status (potential)
- Chat list with recent conversations

**Comments**:
- Threaded comment system
- Reply to posts
- View all comments on a post
- Comment timestamp display

### 6. User Discovery
**Fragments**: `StalkFragment`, `SearchUserProfileFragment`
**Adapters**: `SearchUserProfileAdapter`

- Search by Codeforces handle
- Search by LeetCode username  
- Search by AtCoder handle
- View complete user profiles
- See cross-platform statistics
- Browse user directory

---

## 🛠️ Technologies Used

### Android SDK & Core
- **Minimum SDK**: Android 7.0 (API 24)
- **Target SDK**: Android 14 (API 34)
- **Language**: Java 8
- **Build System**: Gradle 8.0.2

### Firebase Services
- `firebase-auth:22.1.1` - Authentication
- `firebase-database:20.2.2` - Realtime Database
- `firebase-storage:20.2.1` - Cloud Storage
- `firebase-bom:32.5.0` - Bill of Materials

### Google Services
- `play-services-auth:17.0.0` - Google Sign-In
- `google-services:4.3.15` - Google Services Plugin

### UI Libraries
- `material:1.9.0` - Material Design Components
- `constraintlayout:2.1.4` - Constraint Layout
- `recyclerview:1.3.1` - RecyclerView
- `cardview:1.0.0` - CardView
- `viewpager2:1.0.0-alpha03` - ViewPager2
- `circleimageview:3.1.0` - Circular Image View
- `swiperefreshlayout:1.1.0` - Swipe Refresh Layout
- `drawerlayout:1.2.0` - Drawer Layout
- `fragment:1.3.4` - Fragment Library

### Networking Libraries
- `volley:1.2.0` - HTTP library for Codeforces API
- `okhttp3:4.9.1` - HTTP client for LeetCode/AtCoder APIs
- `picasso:2.71828` - Image loading and caching

### Utility Libraries
- `json:20210307` - JSON parsing
- `threetenbp:1.4.0` - Time/Date handling (Java 8 Time backport)
- `ssp-android:1.0.5` - Scalable size unit for text
- `sdp-android:1.0.5` - Scalable size unit for dimensions

### Testing
- `junit:4.13.2` - Unit testing
- `espresso-core:3.5.1` - UI testing
- `androidx.test.ext:junit:1.1.5` - AndroidX Test

---

## 📥 Installation

### Prerequisites
- Android Studio Arctic Fox or later
- JDK 8 or higher
- Android SDK with API 24+
- Firebase account and project setup
- Google Services configuration file (`google-services.json`)

### Setup Steps

1. **Clone the Repository**
   ```bash
   git clone https://github.com/mahmudulyeamim/KodeShell.git
   cd KodeShell
   ```

2. **Firebase Configuration**
   - Create a Firebase project at [Firebase Console](https://console.firebase.google.com/)
   - Add an Android app to your Firebase project
   - Download `google-services.json` and place it in `app/` directory
   - Enable Firebase Authentication (Email/Password and Google Sign-In)
   - Enable Firebase Realtime Database
   - Set up database security rules

3. **Database Structure**
   Set up Firebase Realtime Database with the following structure:
   ```
   - users/
     - userId/
       - firstName, lastName, email, phone, status
       - codeforces, atcoder, leetcode (handles)
       - avatar, postCount, contribution
   
   - posts/
     - postId/
       - userId, content, timestamp
       - upvotes, downvotes, commentCount
   
   - comments/
     - postId/
       - commentId/
         - userId, content, timestamp
   
   - messages/
     - chatId/
       - messageId/
         - senderId, receiverId, message, timestamp
   
   - contests/
     - contestId/
       - name, platform, startTime, duration, link
   ```

4. **Google OAuth Setup**
   - Enable Google Sign-In in Firebase Authentication
   - Add SHA-1 fingerprint to Firebase project
   - Download updated `google-services.json`

5. **Build and Run**
   ```bash
   ./gradlew assembleDebug
   ```
   Or use Android Studio:
   - Open project in Android Studio
   - Sync Gradle files
   - Run on emulator or physical device

### API Keys (Optional)
For full functionality, the app uses public APIs that don't require keys:
- Codeforces API: Public, no key needed
- LeetCode GraphQL: Public endpoint
- Kontests.net: Public API for AtCoder contests

---

## 📁 Project Structure

```
KodeShell/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/kodeshell/
│   │   │   │   ├── Activities/
│   │   │   │   │   ├── MainActivity.java
│   │   │   │   │   ├── LoginActivity.java
│   │   │   │   │   ├── SignupActivity1.java
│   │   │   │   │   ├── SignupActivity2.java
│   │   │   │   │   ├── ChatActivity.java
│   │   │   │   │   ├── ContestInsiderActivity.java
│   │   │   │   │   ├── ContestReminder.java
│   │   │   │   │   ├── ForgotPasswordActivity.java
│   │   │   │   │   ├── OtpActivity.java
│   │   │   │   │   ├── ResetPasswordActivity.java
│   │   │   │   │   ├── SplashActivity.java
│   │   │   │   │   ├── UserList.java
│   │   │   │   │   └── WebviewActivity.java
│   │   │   │   │
│   │   │   │   ├── Fragments/
│   │   │   │   │   ├── HomeFragment.java
│   │   │   │   │   ├── NewsFragment.java
│   │   │   │   │   ├── ContestFragment.java
│   │   │   │   │   ├── ProfileFragment.java
│   │   │   │   │   ├── StalkFragment.java
│   │   │   │   │   ├── ProfileKodeShellFragment.java
│   │   │   │   │   ├── ProfileCodeForcesFragment.java
│   │   │   │   │   ├── ProfileLeetCodeFragment.java
│   │   │   │   │   ├── ProfileAtCoderFragment.java
│   │   │   │   │   ├── EditProfileFragment.java
│   │   │   │   │   ├── UserProfileFragment.java
│   │   │   │   │   ├── NewPostFragment.java
│   │   │   │   │   ├── CommentFragment.java
│   │   │   │   │   ├── AboutUsFragment.java
│   │   │   │   │   └── SearchUserProfileFragment.java
│   │   │   │   │
│   │   │   │   ├── Adapters/
│   │   │   │   │   ├── ContestAdapter.java
│   │   │   │   │   ├── SubmissionAdapter.java
│   │   │   │   │   ├── MessageAdapter.java
│   │   │   │   │   ├── NewsAdapter.java
│   │   │   │   │   ├── CommentAdapter.java
│   │   │   │   │   ├── SearchUserProfileAdapter.java
│   │   │   │   │   └── ProfileViewPagerAdapter.java
│   │   │   │   │
│   │   │   │   ├── ViewHolders/
│   │   │   │   │   ├── PostHolder.java
│   │   │   │   │   ├── ContestHolder.java
│   │   │   │   │   ├── SubmissionHolder.java
│   │   │   │   │   └── NewsHolder.java
│   │   │   │   │
│   │   │   │   ├── Models/
│   │   │   │   │   ├── User.java
│   │   │   │   │   ├── Post.java
│   │   │   │   │   ├── MessegeModel.java
│   │   │   │   │   ├── Codeforces_Contest.java
│   │   │   │   │   └── UserTime.java
│   │   │   │   │
│   │   │   │   ├── APIHelpers/
│   │   │   │   │   ├── CodeforcesAPIHelper.java
│   │   │   │   │   ├── LeetcodeAPIHelper.java
│   │   │   │   │   └── AtcoderAPIHelper.java
│   │   │   │   │
│   │   │   │   └── Utils/
│   │   │   │       └── AlarmReceiver.java
│   │   │   │
│   │   │   ├── res/
│   │   │   │   ├── layout/ (Activity & Fragment layouts)
│   │   │   │   ├── menu/ (Navigation menus)
│   │   │   │   ├── drawable/ (Icons & images)
│   │   │   │   ├── values/ (Strings, colors, themes)
│   │   │   │   └── mipmap/ (App icons)
│   │   │   │
│   │   │   └── AndroidManifest.xml
│   │   │
│   │   ├── test/ (Unit tests)
│   │   └── androidTest/ (Instrumentation tests)
│   │
│   ├── build.gradle (App-level build config)
│   └── google-services.json (Firebase config)
│
├── gradle/ (Gradle wrapper)
├── build.gradle (Project-level build config)
├── settings.gradle (Project settings)
└── README.md (This file)
```

---

## 📸 Screenshots

> **Note**: Add screenshots of your app here to showcase the UI/UX

Recommended screenshots to add:
1. Splash Screen
2. Login/Signup Flow
3. Home Feed with Posts
4. Contest Calendar View
5. Profile Page (All 4 tabs)
6. User Search/Stalk Feature
7. Chat Interface
8. Contest Reminder Setup
9. News Section
10. Edit Profile Screen

---

## 🚀 Future Enhancements

### Potential Features
- [ ] **Dark Mode**: Theme switching for better UX
- [ ] **CodeChef Integration**: Add CodeChef API support
- [ ] **HackerRank Integration**: Include HackerRank profiles
- [ ] **Group Chats**: Create study groups or discussion channels
- [ ] **Problem Recommendations**: AI-based problem suggestions
- [ ] **Virtual Contests**: Host custom contests within the app
- [ ] **Achievement System**: Badges and milestones
- [ ] **Leaderboards**: Platform-wise and overall rankings
- [ ] **Code Sharing**: Share code snippets with syntax highlighting
- [ ] **Editorial Links**: Direct links to problem editorials
- [ ] **Calendar Sync**: Export contests to Google Calendar
- [ ] **Offline Mode**: Cache data for offline viewing
- [ ] **Performance Analytics**: Detailed statistics and graphs
- [ ] **Push Notifications**: Enhanced real-time notifications
- [ ] **Multi-language Support**: Internationalization (i18n)

### Technical Improvements
- [ ] Migrate to **Kotlin** for modern Android development
- [ ] Implement **MVVM Architecture** with LiveData and ViewModel
- [ ] Use **Jetpack Compose** for declarative UI
- [ ] Add **Repository Pattern** for data layer abstraction
- [ ] Implement **Dependency Injection** with Hilt/Dagger
- [ ] Add **Unit Tests** with comprehensive coverage
- [ ] Implement **CI/CD Pipeline** with GitHub Actions
- [ ] Use **Coroutines** for async operations
- [ ] Add **Room Database** for local caching
- [ ] Implement **WorkManager** for background tasks

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Coding Standards
- Follow Android development best practices
- Write clean, documented code
- Test your changes thoroughly
- Update README if adding new features

---

## 📄 License

This project was developed as part of academic coursework for CSE-2216 Application Development Lab.

---

## 👨‍💻 Author

**Mahmudul Yeamin**

- GitHub: [@mahmudulyeamim](https://github.com/mahmudulyeamim)
- Email: [Contact via GitHub]

---

## 📞 Contact & Support

For questions, suggestions, or issues:
- Open an issue on GitHub
- Contact through GitHub profile

---

## 🙏 Acknowledgments

- **Course**: CSE-2216 Application Development Lab
- **APIs Used**: 
  - Codeforces API
  - LeetCode GraphQL API
  - Kontests.net API
- **Libraries**: Special thanks to all open-source library maintainers

---

## 📊 Project Statistics

- **Activities**: 9
- **Fragments**: 16
- **API Integrations**: 3 platforms
- **Features**: 30+ implemented features
- **Lines of Code**: Comprehensive Android application

---

<div align="center">
  <strong>⭐ Star this repository if you find it useful!</strong>
  <br><br>
  Made with ❤️ for the Competitive Programming Community
</div>
