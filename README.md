<img width="835" height="372" alt="image" src="https://github.com/user-attachments/assets/c7149b5b-8178-460c-befb-2374c3da9922" />


## 🛠️ Technical Architecture & System Design Specs

### 1. Client Architecture (Android App)
* **Design Pattern:** Architecture ko strict **MVI (Model-View-Intent)** aur Unidirectional Data Flow (UDF) ke sath architect kiya gaya hai. UI ke paas koi independent state nahi hoti, jisse state race-conditions complete eliminate ho jaati hain.
* **UI Layer:** Pure **Jetpack Compose** ka use kiya kaaya hai. Chat titles list ke liye `LazyColumn` ko custom layout keys ke sath optimize kiya gaya hai taaki high-frequency data chunks receive hone par poorani list-items unwanted recompose na hon.
* **State Management:** ViewModel background execution ke liye `Dispatchers.IO` use karta hai aur UI ko single immutable `StateFlow` pipeline ke through reactive state stream dispatch karta hai.

### 2. Backend Architecture (FastAPI Gateway)
* **Framework Choice:** Backend ke liye **FastAPI (Python)** ko chuna gaya hai kyunki yeh native asynchronous (ASGI) coroutines support karti hai, jo concurrent AI generations ke waqt low-latency networking standard deliver karti hai.
* **Security & Key Isolation:** Android client direct Gemini API se interact nahi karta hai. FastAPI yahan ek secure proxy gateway ki tarah act karta hai, jisse hamari production API keys mobile binary reverse-engineering vectors se 100% isolate rehti hain.
* **Session Lifecycle:** Core security layers ko maintain karne ke liye backend par strict OAuth2/JWT framework integration lagaya gaya hai jo user authentication flow ko handle karta hai.

### 3. Third-Party AI Integration
* **Model Engine:** Core AI layer par **Gemini API (GenAI)** ka integration lagaya gaya hai jo Python SDK endpoints ke dynamic wrapper requests ko asynchronous framework ke through process aur stream karta hai.
