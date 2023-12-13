package com.qasimnawaz019.cartwave.ui.screens.auth.register

import android.content.ContentResolver
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.qasimnawaz019.cartwave.R
import com.qasimnawaz019.cartwave.ui.components.AlertMessageDialog
import com.qasimnawaz019.cartwave.ui.components.CartWaveLabelledCheckBox
import com.qasimnawaz019.cartwave.ui.components.CartWaveOutlinedTextField
import com.qasimnawaz019.cartwave.ui.components.CartWaveOutlinedTextFieldPassword
import com.qasimnawaz019.cartwave.ui.components.LoadingDialog
import com.qasimnawaz019.cartwave.ui.components.SocialButton
import com.qasimnawaz019.cartwave.ui.screens.graphs.Graph
import com.qasimnawaz019.cartwave.utils.navigateTo
import com.qasimnawaz019.domain.dto.login.RegisterRequestDto
import com.qasimnawaz019.domain.model.User
import com.qasimnawaz019.domain.utils.NetworkUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.getViewModel
import java.io.ByteArrayOutputStream

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterScreen(
    navController: NavHostController, viewModel: RegisterScreenViewModel = getViewModel()
) {
    val context = LocalContext.current
    val fNameFocusRequester = remember { FocusRequester() }
    val lNameFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
    val passFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val contentResolver: ContentResolver = context.contentResolver
    val scope = rememberCoroutineScope()

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageBase64 by remember {
        mutableStateOf<String?>(null)
    }

    val getImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            it?.let { uri ->
                imageUri = uri
                scope.launch {
                    imageBase64 = convertImageToBase64(contentResolver, uri)
                }
            }
        }
    )

    var isChecked by remember { mutableStateOf(value = false) }
    val loginState: NetworkUiState<User> by viewModel.networkUiState.collectAsStateWithLifecycle()
    val redirectToHome: Boolean by viewModel.redirectToHome.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = redirectToHome, block = {
        if (redirectToHome) {
            navigateTo(
                navController = navController, destination = Graph.MAIN_GRAPH, true
            )
        }
    })
    when (loginState) {
        is NetworkUiState.Loading -> {
            LoadingDialog()
        }

        is NetworkUiState.Error -> {
            AlertMessageDialog(title = "Something went wrong !",
                message = (loginState as NetworkUiState.Error).error,
                drawable = R.drawable.ic_error_dialog,
                positiveButtonText = "Retry",
                negativeButtonText = "Cancel",
                onPositiveClick = {
                    viewModel.performRegister(
                        RegisterRequestDto(
                            firstName = viewModel.firstNameState.text,
                            lastName = viewModel.lastNameState.text,
                            avatar = imageBase64.toString(),
                            email = viewModel.emailState.text,
                            password = viewModel.passwordState.text
                        )
                    )
                },
                onNegativeClick = { viewModel.emptyUiState() })
        }

        is NetworkUiState.Success -> {
            (loginState as NetworkUiState.Success<User>).data.let {
                viewModel.saveUser(it, isChecked)
            }
        }

        else -> {}
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(30.dp),
    ) {
        item {
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                text = "Hello!",
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        item {
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "Signup to get started",
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )
        }
        item {
            Spacer(modifier = Modifier.size(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = if (imageUri != null) imageUri else R.drawable.ic_person_circle,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .clickable {
                            getImage.launch("image/*")
                        },
                    contentScale = ContentScale.FillBounds
                )
            }
        }
        item {
            Spacer(modifier = Modifier.size(20.dp))
            CartWaveOutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(fNameFocusRequester),
                value = viewModel.firstNameState.text,
                isError = viewModel.firstNameState.error != null,
                errorMessage = viewModel.firstNameState.error,
                onValueChange = {
                    viewModel.onFirstNameChange(it)
                    viewModel.firstNameState.validate()
                },
                label = "First Name",
                iconDrawable = R.drawable.ic_profile,
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(onNext = { lNameFocusRequester.requestFocus() })
            )
        }
        item {
            Spacer(modifier = Modifier.size(10.dp))
            CartWaveOutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(lNameFocusRequester),
                value = viewModel.lastNameState.text,
                isError = viewModel.lastNameState.error != null,
                errorMessage = viewModel.lastNameState.error,
                onValueChange = {
                    viewModel.onLastNameChange(it)
                    viewModel.lastNameState.validate()
                },
                label = "Last Name",
                iconDrawable = R.drawable.ic_profile,
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(onNext = { emailFocusRequester.requestFocus() })
            )
        }
        item {
            Spacer(modifier = Modifier.size(10.dp))
            CartWaveOutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(emailFocusRequester),
                value = viewModel.emailState.text,
                isError = viewModel.emailState.error != null,
                errorMessage = viewModel.emailState.error,
                onValueChange = {
                    viewModel.onEmailChange(it)
                    viewModel.emailState.validate()
                },
                label = "Email",
                keyboardType = KeyboardType.Email,
                iconDrawable = R.drawable.ic_email,
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(onNext = { passFocusRequester.requestFocus() })
            )
        }
        item {
            Spacer(modifier = Modifier.size(10.dp))
            CartWaveOutlinedTextFieldPassword(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(passFocusRequester),
                value = viewModel.passwordState.text,
                isError = viewModel.passwordState.error != null,
                errorMessage = viewModel.passwordState.error,
                onValueChange = {
                    viewModel.onPasswordChange(it)
                    viewModel.passwordState.validate()
                },
                label = "Password",
                imeAction = ImeAction.Done,
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
            )
        }
        item {
            Spacer(modifier = Modifier.size(10.dp))
            CartWaveLabelledCheckBox(
                modifier = Modifier.fillMaxWidth(),
                isChecked = isChecked,
                onCheckedChange = { isChecked = it },
                label = "Remember me"
            )
        }
        item {
            Spacer(modifier = Modifier.size(20.dp))
            Button(
                modifier = Modifier.fillMaxWidth(), onClick = {
                    viewModel.performRegister(
                        RegisterRequestDto(
                            firstName = viewModel.firstNameState.text,
                            lastName = viewModel.lastNameState.text,
                            avatar = imageBase64.toString(),
                            email = viewModel.emailState.text,
                            password = viewModel.passwordState.text
                        )
                    )
                }, colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledBackgroundColor = MaterialTheme.colorScheme.surfaceVariant
                ), enabled = viewModel.ifAllDataValid()
            ) {
                Text(text = "Signup")
            }
        }
        item {
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "or continue with",
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                fontSize = 12.sp
            )
        }
        item {
            Spacer(modifier = Modifier.size(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
            ) {
                SocialButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    drawable = R.drawable.ic_google,
                    label = "Google"
                )
                Spacer(modifier = Modifier.size(10.dp))
                SocialButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    drawable = R.drawable.ic_facebook,
                    label = "Facebook"
                )
            }
        }
        item {
            Spacer(modifier = Modifier.size(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Already have an account ? ",
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    fontSize = 12.sp
                )
                Text(
                    modifier = Modifier.clickable { navController.popBackStack() },
                    text = "Login",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 12.sp
                )
            }
        }
    }
}

suspend fun convertImageToBase64(contentResolver: ContentResolver, uri: Uri): String? {
    return withContext(Dispatchers.IO) {
        val inputStream = contentResolver.openInputStream(uri)
        val bytes = ByteArrayOutputStream()
        inputStream?.use { input ->
            input.copyTo(bytes)
        }

        val imageData: ByteArray = bytes.toByteArray()
        android.util.Base64.encodeToString(imageData, android.util.Base64.DEFAULT)
    }
}

