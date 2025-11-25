# DialektoGO

**Learn Tagalog the best way possible.**

DialektoGO is a Progressive Web App (PWA) designed to help users learn Tagalog through interactive lessons, practice activities, and AI-powered assistance.

## 🌟 Features

- **Interactive Lessons**: Structured learning path with multiple sections covering various aspects of Tagalog
- **Practice Activities**: Reinforce your learning through engaging practice exercises
- **Dictionary**: Access a comprehensive Tagalog-English dictionary
- **AI Chat Assistant**: Get instant help with your language learning questions
- **Story Mode**: Learn through contextual storytelling
- **Writing Practice**: Improve your Tagalog writing skills
- **Progressive Web App**: Install and use offline on any device
- **User Authentication**: Create an account to track your progress

## 🚀 Getting Started

### Prerequisites

- Node.js (for running the development server)
- A modern web browser
- Firebase account (for authentication and hosting)

### Installation

1. Clone the repository:
```bash
git clone https://github.com/Moxy004/DialektoGo.git
cd DialektoGo
```

2. Install dependencies:
```bash
npm install
```

3. Set up environment variables:
Create a `.env` file in the root directory with your API keys and configuration.

4. Start the development server:
```bash
npm start
```

## 📂 Project Structure

```
DialektoGo/
├── activities/          # Practice activities and exercises
├── dictionary/          # Dictionary feature files
├── lessons/            # Lesson content and structure
├── profile/            # User profile management
├── icons/              # Application icons
├── images/             # Image assets
├── screenshots/        # Application screenshots
├── index.html          # Landing page
├── home.html           # Main application dashboard
├── login.html          # User login page
├── signup.html         # User registration page
├── add_user.html       # User management
├── listening.html      # Listening exercises
├── styles.css          # Application styling
├── script.js           # Main JavaScript logic
├── service-worker.js   # PWA service worker
├── server.js           # Express server for API
├── manifest.json       # PWA manifest
├── firebase.json       # Firebase configuration
└── package.json        # Project dependencies
```

## 🛠️ Technologies Used

- **Frontend**: HTML5, CSS3, JavaScript
- **Backend**: Node.js, Express.js
- **APIs**: 
  - Groq SDK (AI chat functionality)
  - Axios (HTTP requests)
- **Hosting**: Firebase
- **PWA**: Service Workers, Web App Manifest
- **Authentication**: Firebase Authentication

## 📦 Dependencies

```json
{
  "axios": "^1.9.0",
  "cors": "^2.8.5",
  "dotenv": "^16.5.0",
  "express": "^5.1.0",
  "groq-sdk": "^0.21.0"
}
```

## 🎯 Key Features

### Learning Path
- **Section-based Learning**: Progress through structured sections
- **Trophy System**: Earn achievements as you complete lessons
- **Practice Mode**: Dedicated practice exercises for each section
- **Story Mode**: Learn through immersive storytelling

### AI Assistant
- Real-time chat support for language learning questions
- Powered by Groq SDK for intelligent responses
- Integrated seamlessly into the learning interface

### Progressive Web App
- Installable on desktop and mobile devices
- Offline functionality with service workers
- Native app-like experience

## 🌐 Deployment

The application is configured for deployment on:
- **Firebase Hosting**: Main hosting platform
- **Railway**: Backend server deployment (Express API)

## 📱 PWA Installation

Users can install DialektoGO as a Progressive Web App on their devices:

1. Visit the website
2. Click the install prompt or "Add to Home Screen"
3. Launch the app from your device like a native application

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

This project is available for use and modification.

## 👤 Authors

- GitHub: [@Moxy004](https://github.com/Moxy004)
- GitHub: [@PatrickIlagan](https://github.com/PatrickIlagan)

## 🔗 Links

- Repository: [https://github.com/Moxy004/DialektoGo](https://github.com/Moxy004/DialektoGo)

## 📧 Support

If you have any questions or need help, please open an issue in the GitHub repository.

---

**Start your Tagalog learning journey today with DialektoGO!** 🚀
