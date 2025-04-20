package com.example.hoseinvand.jetpack_1

import android.os.Bundle
import android.view.Menu
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.magnifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hoseinvand.jetpack_1.ui.theme.JetPack_1Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // SimpleDrawer()
            CompositionLocalProvider { LocalLayoutDirection provides LayoutDirection.Rtl }
            MyDrawerApp()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MyDrawerApp() {                                                                    //اینجا باید در ابزار navigation ببریم تا نمایش بده و خاصیت کشویی بودن رو بهش بدیم
        val myDrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)        // برای اینکه بفهمیم وضعیتش باز یا بسته اس.که در پیش فرض بسته اس
        val scope = rememberCoroutineScope()                                              // برای باز و بسته کردن

        ModalNavigationDrawer(                                                            //همون navigationview در xml
            scrimColor = Color.Gray,
            drawerState = myDrawerState,
            drawerContent = { DrawerContent(onItemClick = { scope.launch { myDrawerState.close() } }) }) {      //این برای بسته بودن
            Scaffold(                                                                      // کامپوز است برای قرار دادن وBottomBar,TopAppbar
                topBar = {
                    TopAppBar(title = { Text("خانه") }, navigationIcon = {
                        IconButton(onClick = { scope.launch { myDrawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                    )
                }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    contentAlignment = Alignment.Center
                ) {
                    Text("محتوای اصلی اینحاست ")
                }
            }
        }
    }


    @Composable
    fun DrawerContent(onItemClick: () -> Unit) {                                     //محتوای سطر Drawer
        LazyColumn(
            modifier = Modifier                                             // تفاوت LazyColumn با Column: داینامیک .که برای فهرست های طولانی استفاده میشه .در Recy از Lazy استفاده میشه.خودش قابلیت Scroll داره.Column ساده تر و کوچکتر هست .برای فهرست های کوچکتر هست.
                .fillMaxSize()
                .background(Color.Red)
                .padding(WindowInsets.statusBars.asPaddingValues())                     //از نوار بالا که ساعت داره فاصله میگیره
                .padding(16.dp)                                                         //این padding قبل از دستور بالا میشه margin
        ) {
            item {                                                                  // ساخت آیتم ها.میخوام محتوا به آیتمی بدم که در DrawerItem ساختم
                Text(
                    "منو",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                )
            }

            item {
                DrawerItem(
                    "خانه",
                    Icons.Default.Home,
                    myOnItemClick = onItemClick
                )       // این DrawerItem همون متد drawerItem هست
            }
            item {
                DrawerItem("تنظیمات", Icons.Default.Settings, myOnItemClick = onItemClick)  // اینجا
            }
            item {
                DrawerItem("تنظیمات", Icons.Default.Settings, myOnItemClick = onItemClick)
            }
        }
    }


    //@Preview(showBackground = true)                                                                   متدی که ورودی داشته باشه پریویو قابل فراخوانی نیست
    @Composable
    fun DrawerItem(
        text: String,
        icon: ImageVector,
        myOnItemClick: () -> Unit
    ) {                                  //نحوه قرار گیری آیتم ها,که ی آیتم داره و یک تکست
        Row(
            Modifier                                                                                    //چون میخواهیم نوشته هامون سطری باشن از Row استفاده میکنیم
                .fillMaxSize()                                                                              // تمام صفحه را در اختیار داشته باشیم
                .clickable(onClick = myOnItemClick)                                                                                //خاصیت کلیک
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {                                                                                              //در براکت 3 تا خصوصیت دادیم
            Icon(
                icon,
                contentDescription = null
            )                                                       //در اینجا ترتیب قرار گیری مهمه
            Spacer(modifier = Modifier.width(16.dp))
            Text(text, fontSize = 16.sp)

        }
    }


    // حالا ما چطور اینو به کامپوز بعدی بدم ؟باید داخل ورودیش مشخص بکنم که در ادامه من در DrawerContent اینو فراخوانی کنم با مقدار دادن به این ها سطرم تشکیل بشه ُحالا چیکار کنم در ورودی تکست از نوع استرینگ و.....


//    @OptIn(ExperimentalMaterial3Api::class)
//    @Composable
//    fun MyDrawerApp() {
//        val myDrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//        val scope = rememberCoroutineScope()
//
//        ModalNavigationDrawer(
//            scrimColor = Color.Gray,
//            drawerState = myDrawerState,
//            drawerContent = { DrawerContent(onItemClick = { scope.launch { myDrawerState.close() } }) })
//        {
//            Scaffold(topBar = {
//                TopAppBar(
//                    title = { Text("Home") },
//                    navigationIcon = { IconButton(onClick = { scope.launch { myDrawerState.open() } }){
//                        Icon(Icons.Default.Menu, contentDescription = "Menu")
//                    } })
//            }){
//                Box(modifier = Modifier.fillMaxSize().padding(it), contentAlignment = Alignment.Center){
//                    Text("ieurgherlu")
//                }
//            }
//
//        }
//
//
//    }
////        val myDrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
////        val scope = rememberCoroutineScope()
////        ModalNavigationDrawer(
////            scrimColor = Color.White,
////            drawerState = myDrawerState,
////            drawerContent = {
////                DrawerContent(onItemClick = {
////                    scope.launch { myDrawerState.close() }
////                })
////            }
////        )
////        {
////
////            Scaffold(
////                topBar = {
////                TopAppBar(
////                    title = { Text("خانه") },
////                    navigationIcon = {
////                        IconButton(onClick = {
////                            scope.launch { myDrawerState.open() }
////                        }) {
////                            Icon(Icons.Default.Menu,contentDescription = "Menu")
////                        }
////                    }
////                )
////            }
////            ) { padding ->
////                Box(
////                    modifier = Modifier
////                        .fillMaxSize()
////                        .padding(padding),
////                    contentAlignment = Alignment.Center
////                ) {
////                    Text("محتوای اصلی اینجاست")
////                }
////            }
////
////        }
////    }
//
//    @Composable
//    fun DrawerContent(onItemClick: () -> Unit) {
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.Blue)
//                .padding(WindowInsets.statusBars.asPaddingValues())
//                .padding(16.dp)
//        ) {
//            item {
//                Text(
//                    "منو",
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 20.sp,
//                    modifier = Modifier.padding(bottom = 16.dp)
//                )
//            }
//            item {
//                DrawerItem("خانه", Icons.Default.Home, onItem = onItemClick)
//            }
//            item {
//                DrawerItem("تنظیمات", Icons.Filled.Settings, onItem = onItemClick)
//            }
//            item {
//                DrawerItem("مورد علاقه", Icons.Filled.Favorite, onItem = onItemClick)
//            }
//        }
//    }
//
//
//    @Composable
//    fun DrawerItem(text: String, icon: ImageVector, onItem: () -> Unit) {
//        Row(
//            modifier = Modifier
//                .fillMaxSize()
//                .clickable(onClick = onItem)
//                .padding(vertical = 16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Icon(icon, contentDescription = null)
//            Spacer(modifier = Modifier.width(16.dp))
//            Text(text, fontSize = 16.sp)
//        }
//    }
//

//    @Preview
//    @Composable
//    fun showText() {
//        Text("Hello Maryam", modifier = Modifier)
//    }
//
//
//    @Composable
//    fun MyBox() {
//        Box(
//            Modifier
//                .size(100.dp)
//                .padding(16.dp)
//                .background(Color.Blue)
//        )
//    }
//
//    @Preview
//    @Composable
//    fun ColumnShowCase() {
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text("Column")
//            MyBox()
//            MyBox()
//            MyBox()
//            MyBox()
//        }
//    }
//
//    @Preview
//    @Composable
//    fun RowShowCase() {
//        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically)
//        {
//            Text("Row")
//            MyBox()
//            MyBox()
//            MyBox()
//
//        }
//    }
//
//    @Preview(showBackground = true)
//    @Composable
//    fun SimpleDrawer() {
//        var expanded by remember { mutableStateOf(false) }
//        var selectedOption by remember { mutableStateOf("selected") }
//        Column(modifier = Modifier.padding(8.dp)) {
//            Spacer(Modifier.size(20.dp))
//            Button(onClick = { expanded = true }) {
//                Text(text = selectedOption)
//            }
//        }
//
//        DropdownMenu(expanded = expanded,
//            onDismissRequest = { expanded = false }) {
//        }
//
//    }

}