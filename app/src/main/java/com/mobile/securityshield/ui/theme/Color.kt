package com.mobile.securityshield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mobile.securityshield.ui.theme.MobileSecurityShieldTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileSecurityShieldTheme {
                SecurityApp()
            }
        }
    }
}

data class ThreatAlert(
    val title: String,
    val risk: String,
    val detail: String
)

data class LicensePlan(
    val title: String,
    val duration: String,
    val price: String,
    val devices: String,
    val tag: String
)

data class VoiceCommand(
    val label: String,
    val command: String
)

@Composable
fun SecurityApp() {
    val alerts = remember {
        listOf(
            ThreatAlert("Suspicious app permission", "High", "Accessibility and overlay access requested by an untrusted store app."),
            ThreatAlert("Unsafe network route", "Medium", "Connection to a risky domain pattern was flagged by the AI firewall."),
            ThreatAlert("Phishing link", "High", "A malicious deep-link pattern matched known phishing signatures."),
        )
    }

    val plans = remember {
        listOf(
            LicensePlan("Starter", "3 Months", "₹499", "1 Device", "Basic"),
            LicensePlan("Shield Pro", "6 Months", "₹899", "3 Devices", "Popular"),
            LicensePlan("Family Guard", "12 Months", "₹1499", "10 Devices", "Best Value")
        )
    }

    val commands = remember {
        listOf(
            VoiceCommand("Scan my apps", "scan my apps"),
            VoiceCommand("Block suspicious app", "block suspicious app"),
            VoiceCommand("Show security status", "show my security status"),
            VoiceCommand("Check network safety", "check network safety")
        )
    }

    var selectedTab by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            NavigationBar {
                val tabs = listOf(
                    stringResource(R.string.dashboard),
                    stringResource(R.string.scan),
                    stringResource(R.string.licenses),
                    stringResource(R.string.settings)
                )

                val icons = listOf(
                    Icons.Default.Home,
                    Icons.Default.Shield,
                    Icons.Default.MonetizationOn,
                    Icons.Default.Settings
                )

                tabs.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = { Icon(imageVector = icons[index], contentDescription = tab) },
                        label = { Text(tab) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.18f),
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            when (selectedTab) {
                0 -> DashboardContent(alerts)
                1 -> ScanContent()
                2 -> LicensePlansContent(plans)
                else -> SettingsContent(commands)
            }
        }
    }
}

@Composable
fun DashboardContent(alerts: List<ThreatAlert>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            HeaderCard(
                title = stringResource(R.string.device_protected),
                subtitle = stringResource(R.string.ai_security_suite),
                value = "98%",
                status = stringResource(R.string.safe_status)
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(label = stringResource(R.string.detected_threats), value = "03")
                StatCard(label = stringResource(R.string.devices_secure), value = "05")
            }
        }

        item {
            Text(
                text = stringResource(R.string.active_risks),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(alerts) { alert ->
            ThreatCard(alert)
        }
    }
}

@Composable
fun ScanContent() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        HeaderCard(
            title = stringResource(R.string.scan_title),
            subtitle = stringResource(R.string.scan_description),
            value = "Ready",
            status = stringResource(R.string.scan_status)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = { },
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(R.string.scan_apps))
            }

            Button(
                onClick = { },
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(R.string.scan_network))
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            shape = RoundedCornerShape(18.dp)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(R.string.ai_threat_review),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.ai_summary),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun LicensePlansContent(plans: List<LicensePlan>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text(
                text = stringResource(R.string.select_plan),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        items(plans) { plan ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (plan.tag == "Popular") MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(plan.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(plan.tag, color = MaterialTheme.colorScheme.primary)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(plan.duration, style = MaterialTheme.typography.bodyLarge)
                    Text(plan.price, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Text(plan.devices, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { }) {
                        Text(stringResource(R.string.activate_plan))
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsContent(commands: List<VoiceCommand>) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var groqKey by rememberSaveable { mutableStateOf("") }
    var sarvamKey by rememberSaveable { mutableStateOf("") }
    var locale by rememberSaveable { mutableStateOf("English") }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text(
                text = stringResource(R.string.account_security),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(stringResource(R.string.username)) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(R.string.password)) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            OutlinedTextField(
                value = groqKey,
                onValueChange = { groqKey = it },
                label = { Text(stringResource(R.string.groq_api_key)) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            OutlinedTextField(
                value = sarvamKey,
                onValueChange = { sarvamKey = it },
                label = { Text(stringResource(R.string.sarvam_api_key)) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            OutlinedTextField(
                value = locale,
                onValueChange = { locale = it },
                label = { Text(stringResource(R.string.language)) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Text(
                text = stringResource(R.string.voice_commands),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(commands) { command ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(command.label, fontWeight = FontWeight.Bold)
                        Text(command.command, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Icon(Icons.Default.Mic, contentDescription = null)
                }
            }
        }
    }
}

@Composable
fun HeaderCard(title: String, subtitle: String, value: String, status: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(Modifier.padding(18.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Shield, contentDescription = null)
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(0xFF3DDC97).copy(alpha = 0.18f))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(status, color = Color(0xFF0B8F5A), fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(subtitle, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f))
            Spacer(modifier = Modifier.height(18.dp))
            Text(value, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun StatCard(label: String, value: String) {
    Card(
        modifier = Modifier.weight(1f),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(label, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ThreatCard(alert: ThreatAlert) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(
                        when (alert.risk) {
                            "High" -> Color(0xFFFF6B6B).copy(alpha = 0.16f)
                            "Medium" -> Color(0xFFFFC857).copy(alpha = 0.18f)
                            else -> Color(0xFF5AC8FA).copy(alpha = 0.16f)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (alert.risk == "High") Icons.Default.Warning else Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = if (alert.risk == "High") Color(0xFFFF4D4D) else Color(0xFF1EBA73)
                )
            }

            Column(Modifier.weight(1f)) {
                Text(alert.title, fontWeight = FontWeight.Bold)
                Text(alert.detail, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Text(alert.risk, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        }
    }
}
