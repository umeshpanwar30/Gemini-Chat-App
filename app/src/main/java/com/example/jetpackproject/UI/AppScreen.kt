package com.example.jetpackproject.UI

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpackproject.Interface.ApiInterface
import com.example.jetpackproject.Model.ChatMessage
import com.example.jetpackproject.Model.ChatSession
import com.example.jetpackproject.Object.RetrofitClient.apiService
import com.example.jetpackproject.R
import com.example.jetpackproject.ViewModels.LoginViewModel
import com.example.jetpackproject.ViewModels.RegisterViewModel
import kotlinx.coroutines.launch


@Composable
fun RegisterScreen(viewModel: RegisterViewModel, onRegisterSuccess: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Create Account", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = pass,
            onValueChange = { pass = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        if (state.error != null) {
            Text(text = state.error!!, color = Color.Red, modifier = Modifier.padding(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.performRegister(name, email, pass) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = !state.isLoading
        ) {
            if (state.isLoading) Text("Registering...") else Text("Sign Up")
        }

        LaunchedEffect(state.isLoginSuccess) {
            if (state.isLoginSuccess) {
                onRegisterSuccess()
            }
        }
    }
}

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: (String) -> Unit,
    onNavigateToRegister: () -> Unit
) {
    var user by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = user,
            onValueChange = { user = it },
            label = { Text("Username") },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)    // Text color
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = pass,
            onValueChange = { pass = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)    // Text color

        )

        if (state.error != null) {
            Text(text = state.error!!, color = Color.Red)
        }

        Button(
            onClick = { viewModel.performLogin(user, pass) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp), shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(Color.Blue)
        ) {
            Text("Login")
        }
        // LoginScreen ke andar Button ke niche add karein
        Spacer(modifier = Modifier.height(16.dp))
        androidx.compose.material3.TextButton(onClick = onNavigateToRegister) {
            Text("Don't have an account? Register Now")
        }

        // Agar login successful ho gaya toh navigate karo
        LaunchedEffect(state.isLoginSuccess) {
            if (state.isLoginSuccess) {
                onLoginSuccess(user)
            }
        }
    }
}


// 1. ModalNavigationDrawer: Yeh side slider hai
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(apiService: ApiInterface) { // apiService yahan pass karo
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Real-time messages store karne ke liye

    val historyList = remember { mutableStateListOf<ChatSession>() }
    val chatMessages = remember { mutableStateListOf<ChatMessage>() }
    var currentSessionId by remember { mutableStateOf(java.util.UUID.randomUUID().toString()) }
    val userId = 1 // Abhi ke liye hardcoded, baad mein auth se lena
// 'val' ki jagah 'var' use karein aur 'by' keyword ka istemal karein
    var isLoading by remember { mutableStateOf(false) }

    // HomeScreen ke andar top par
    fun refreshHistory() {
        scope.launch {
            try {
                val response = apiService.getHistory(userId)
                // Duplicate check locally before adding
                val uniqueResponse = response.distinctBy { it.session_id }

                historyList.clear()
                historyList.addAll(uniqueResponse)
            } catch (e: Exception) {
                Log.e("HistoryError", e.message ?: "Error")
            }
        }
    }

    LaunchedEffect(Unit) {
        try {
            val response =
                apiService.getHistory(userId) // ApiInterface mein ye endpoint add karna padega
            historyList.clear()
            historyList.addAll(response)
        } catch (e: Exception) {
            Log.e("HistoryError", e.message ?: "Error")
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Chat History",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Drawer mein history dikhane ke liye
                // HomeScreen.kt ke andar Drawer wala hissa
                // ModalDrawerSheet ke andar, History list se pehle ye button dalo
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = "New Chat",
                            style = MaterialTheme.typography.labelLarge
                        )
                    },
                    selected = false,
                    onClick = {
                        scope.launch {
                            // 1. Screen se purane messages hatao
                            chatMessages.clear()

                            // 2. Ek nayi unique ID banao (Isse backend nayi entry karega)
                            currentSessionId = java.util.UUID.randomUUID().toString()

                            // 3. Drawer band karo
                            drawerState.close()
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    icon = {
                        Icon(
                            Icons.Default.AddCircle,
                            contentDescription = null
                        )
                    } // Add icon use karo
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))


                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(
                        items = historyList,
                        key = { it.session_id } // Ye duplicate views ko rokega
                    ) { session ->
                        NavigationDrawerItem(
                            label = { Text(text = session.title ?: "Untitled Chat") },
                            selected = session.session_id == currentSessionId,
                            // LazyColumn ke items mein
                            onClick = {
                                scope.launch {
                                    chatMessages.clear()

                                    // Purani history ke messages load karo (Jo pehle discuss kiya tha)
                                    val oldMessages =
                                        apiService.getSessionMessages(session.session_id)

                                    // CRITICAL: currentSessionId ko purane wala set karo
                                    currentSessionId = session.session_id

                                    oldMessages.forEach { msg ->
                                        chatMessages.add(ChatMessage(msg.prompt, true))
                                        chatMessages.add(ChatMessage(msg.response, false))
                                    }
                                    drawerState.close()
                                }

                            },
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            icon = { Icon(Icons.Rounded.Email, contentDescription = null) }
                        )
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Good Morning, Umesh")
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = { Spacer(modifier = Modifier.width(48.dp)) }
                )
            },
            bottomBar = {
                ChatInputBar(
                    isLoading = isLoading,
                    onSendMessage = { userMsg ->
                        chatMessages.add(ChatMessage(userMsg, true))
                        isLoading = true // State update

                        scope.launch {
                            try {
                                val response = apiService.getGeminiResponse(
                                    userMsg,
                                    currentSessionId,
                                    userId
                                )
                                val botReply =
                                    response.response ?: "Bhai, Gemini ne jawab nahi diya."
                                chatMessages.add(ChatMessage(botReply, false))
                                refreshHistory()
                            } catch (e: Exception) {
                                chatMessages.add(ChatMessage("Error: ${e.message}", false))
                                Log.e("ChatError", "Network issue: ${e.message}")
                            } finally {
                                isLoading = false // Jawab aate hi screen normal
                            }
                        }
                    }
                )
            }
        ) { paddingValues ->
            // Box use karne se items ek ke upar ek (Z-axis) layer hote hain
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                if (chatMessages.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Aapka Stylish Icon (Optional)
                        Icon(
                            painter = painterResource(id = R.drawable.gemini), // Ya koi Gemini jaisa icon
                            contentDescription = null,
                            modifier = Modifier.size(80.dp),
                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // ANIMATED TEXT YAHAN HAI
                        TypingWelcomeText(fullText = "How can I help you today, Umesh?")
                    }
                } else {
                    // 1. Bottom Layer: Chat List
                    ChatList(
                        messages = chatMessages,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                // 2. Top Layer: Full Screen Overlay (Sirf loading ke waqt)
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.6f)) // Dark background overlay
                            .pointerInput(Unit) {}, // Isse niche ke clicks block ho jayenge
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(50.dp),
                                color = MaterialTheme.colorScheme.primary,
                                strokeWidth = 4.dp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "My Mind is thinking...",
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChatList(messages: List<ChatMessage>, modifier: Modifier = Modifier) {
    val listState = rememberLazyListState()

    // Jab naya message aaye, apne aap scroll niche ho jaye
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)
    ) {
        items(
            items = messages,
            // unique key se performance badh jati hai
            key = { it.hashCode() }
        ) { message ->
            ChatBubble(text = message.text, isUser = message.isUser)
        }
    }
}


@Composable
fun ChatText(message: String, isUser: Boolean) {
    val textColor = if (isUser) Color.White else MaterialTheme.colorScheme.onSurface

    Column(modifier = Modifier.padding(12.dp)) {
        val lines = message.split("\n")
        lines.forEach { line ->
            if (line.isNotBlank()) {
                val annotatedString = buildAnnotatedString {
                    val cleanLine =
                        if (line.trim().startsWith("*") || line.trim().startsWith("-")) {
                            append(" • ")
                            line.trim().removePrefix("*").removePrefix("-").trim()
                        } else {
                            line
                        }

                    val parts = cleanLine.split("**")
                    parts.forEachIndexed { index, part ->
                        if (index % 2 == 1) {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(part)
                            }
                        } else {
                            append(part)
                        }
                    }
                }

                Text(
                    text = annotatedString,
                    style = MaterialTheme.typography.bodyLarge,
                    color = textColor,
                    lineHeight = 24.sp
                )
            } else {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ChatBubble(text: String?, isUser: Boolean) { // String? matlab nullable
    val safeText = text ?: "Empty Message" // Agar null ho toh ye dikhega

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        Surface(
            color = if (isUser) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.secondaryContainer,
            shape = RoundedCornerShape(12.dp)
        ) {
            ChatText(message = safeText, isUser = isUser)
        }
    }
}

@Composable
fun TypingWelcomeText(fullText: String) {
    var displayedText by remember { mutableStateOf("") }

    // Text typing animation logic
    LaunchedEffect(fullText) {
        fullText.forEachIndexed { index, _ ->
            displayedText = fullText.substring(0, index + 1)
            kotlinx.coroutines.delay(50) // Speed of typing
        }
    }

    Text(
        text = displayedText,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(24.dp)
    )
}

@Composable

fun ChatInputBar(
    isLoading: Boolean, // Naya parameter loader handle karne ke liye
    onSendMessage: (String) -> Unit
) {
    var message by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = message,
            onValueChange = { message = it },
            placeholder = { Text("Ask me anything...") },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(24.dp), // Thoda modern look
            enabled = !isLoading, // Loading ke waqt typing band
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            onClick = {
                if (message.isNotBlank() && !isLoading) {
                    onSendMessage(message)
                    message = ""
                }
            },
            //enabled = !isLoading // Loading ke waqt button disable rakho
        ) {
            // YAHAN HONA CHAHIYE LOADER LOGIC

            Icon(
                Icons.Default.Send,
                contentDescription = "Send",
                tint = if (message.isNotBlank()) MaterialTheme.colorScheme.primary else Color.Gray
            )
        }
    }
}


@Composable
fun FullScreenLoader() {
    // Ye Box poori screen ko cover kar lega
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)) // Screen thodi dark ho jayegi (untouchable)
            .pointerInput(Unit) {}, // Isse background clicks block ho jayenge
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Aapka stylish loader
            CircularProgressIndicator(
                modifier = Modifier.size(64.dp),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 6.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Gemini is thinking...",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = viewModel()
    val registerViewModel: RegisterViewModel = viewModel() // Register VM add kiya
    val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)

    val startDest = if (isLoggedIn) "home/Umesh" else "login"

    NavHost(navController = navController, startDestination = startDest) {
        // --- Login Screen ---
        composable("login") {
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = { userName ->
                    navController.navigate("home/$userName") {
                        popUpTo("login") {
                            inclusive = true
                        } // Login back press par nahi aana chahiye
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }

        // --- Register Screen ---
        composable("register") {
            RegisterScreen(
                viewModel = registerViewModel,
                onRegisterSuccess = {
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                }
            )
        }

        // --- Home Screen ---
        composable("home/{userName}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("userName")
            HomeScreen(apiService)
        }
    }

}