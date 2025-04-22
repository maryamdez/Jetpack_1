package com.jetpack.hoseinvand_jetpack_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.clipScrollableContainer
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.hoseinvand_jetpack_1.ui.theme.Jetpack_1Theme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.google.accompanist.coil.rememberCoilPainter
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //TextSample()       Text خاصیت های
            // ButtonExample()      Button خاصیت های مختلف
            //ImageNetExample()   گرفتن عکس از سرور
            MyDrawerApp()

        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MyDrawerApp() {                                                                    //اینجا باید در ابزار navigation ببریم تا نمایش بده و خاصیت کشویی بودن رو بهش بدیم
        val myDrawerState =
            rememberDrawerState(initialValue = DrawerValue.Closed)        // برای اینکه بفهمیم وضعیتش باز یا بسته اس.که در پیش فرض بسته اس
        val scope =
            rememberCoroutineScope()                                              // برای باز و بسته کردن

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
                    Text("محتوای ")
                }
            }
        }
    }


    @Composable
    fun DrawerContent(onItemClick: () -> Unit) {                                     //محتوای سطر Drawer
        LazyColumn(
            modifier = Modifier                                             // تفاوت LazyColumn با Column: داینامیک .که برای فهرست های طولانی استفاده میشه .در Recy از Lazy استفاده میشه.خودش قابلیت Scroll داره.Column ساده تر و کوچکتر هست .برای فهرست های کوچکتر هست.
                .fillMaxHeight()
                .width(300.dp)
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
                .fillMaxWidth()                                                                              // تمام صفحه را در اختیار داشته باشیم
                .clickable(onClick = myOnItemClick)                                                                                //خاصیت کلیک
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {                                                                                              //در براکت 3 تا خصوصیت دادیم
            Icon(
                icon,
                contentDescription = null
            )                                                       //در اینجا ترتیب قرار گیری مهمه
            Spacer(modifier = Modifier.width(16.dp))
            Text(text, fontSize = 30.sp)

        }
    }

}






























/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDrawerApp() {
    val myDrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        scrimColor = Color.Blue,
        drawerState = myDrawerState,
        drawerContent = { DrawerContent(onItemClick = { scope.launch { myDrawerState.close() } }) }) {
    Scaffold(topBar = {
            TopAppBar(title = { Text("خانه") }, navigationIcon = {
                IconButton(onClick = {
                    scope.launch {
                        myDrawerState.open() } }) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                }
            }
            )
    }
    ) {
            Box(
                modifier = Modifier.fillMaxSize().padding(it),
                contentAlignment = Alignment.Center
            ) {
                Text(" Hello ")
            }
    }
    }
}


                @Composable
                fun DrawerContent(onItemClick: () -> Unit) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Green)
                            .padding(WindowInsets.statusBars.asPaddingValues())
                    ) {
                        item {
                            Text("خانه", modifier = Modifier, fontSize = 10.sp)
                        }
                        item {
                            DrawerItem(
                                text = "خانه",
                                icon = Icons.Default.Home,
                                onItemClick = onItemClick
                            )
                        }
                        item {
                            DrawerItem(
                                text = "تنظیمات",
                                icon = Icons.Default.Settings,
                                onItemClick = onItemClick
                            )
                        }
                        item {
                            DrawerItem(
                                text = "مورد علاقه",
                                icon = Icons.Default.Favorite,
                                onItemClick = onItemClick
                            )
                        }
                    }

                }


                @Composable
                fun DrawerItem(text: String, icon: ImageVector, onItemClick: () -> Unit) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(icon, contentDescription = null)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text, fontSize = 16.sp)
                    }
                }
        }*/

/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerMyApp() {
    val myDrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerContent = { DrawerContent(onItemClicked = { scope.launch { myDrawerState.close() } }) },
        drawerState = myDrawerState,
        scrimColor = Color.Blue
    )
    {
        Scaffold(topBar = {
            TopAppBar(
                title = { Text("خانه") },
                navigationIcon = {
                    IconButton(onClick = { scope.launch { myDrawerState.open() } }) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = "Menu"
                        )
                    }
                })
        }) {padding->
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center){
                Text("خانه")
            }
        }
    }


}
*//* val myDrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
     val scope = rememberCoroutineScope()
     ModalNavigationDrawer(
         drawerState = myDrawerState,
         drawerContent = { DrawerContent(onItemClicked = { scope.launch { myDrawerState.close() } }) }) {
         Scaffold(
             topBar = {
             TopAppBar(title = { Text("خانه") }, navigationIcon = {
                 IconButton(onClick = { scope.launch { myDrawerState.open() } }) {
                     Icon(Icons.Default.Menu, contentDescription = "Menu")
                 }
             }
             )
         }){
             Box (modifier = Modifier.fillMaxSize().padding(it), contentAlignment = Alignment.Center){
                 Text("محتوای اصلی اینجاست")
             }
         }
     }
 }*//*


    @Composable
    fun DrawerContent(onItemClicked: () -> Unit) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(WindowInsets.statusBars.asPaddingValues())
                .padding(16.dp)
        ) {
            item {
                Text(
                    "منو",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            item {
                DrawerItem("خانه", Icons.Default.Home, click = onItemClicked)
            }

            item {
                DrawerItem("تنظیمات", Icons.Default.Settings, click = onItemClicked)
            }
            item {
                DrawerItem("درباره ما", Icons.Default.Info, click = onItemClicked)
            }
            item {
                DrawerItem("علاقه مندی", Icons.Default.Favorite, click = onItemClicked)
            }


        }

    }


    @Composable
    fun DrawerItem(text: String, icon: ImageVector, click: () -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = click)
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Icon(icon, contentDescription = null)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text, fontSize = 16.sp)
        }


    }


    @Composable
    fun ImageNetExample() {
        Row(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
                .shadow(4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            var req by remember { mutableStateOf("") }
            val painter = rememberCoilPainter(req)
            Image(
                painter = painter, contentDescription = "Ali khar asssssst", modifier = Modifier
                    .width(200.dp)
                    .height(150.dp)
                    .clip(RoundedCornerShape(10))
                    .background(Color.Blue),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(40.de", fonp))
                Text("Image Net ExampltSize = 10.sp)

                Button(onClick = { req = "https://picsum.photos/150/100" }) {
                    Text("image")
                    Spacer(Modifier.height(30.dp))
                }


            }

        }
    }

    @Preview(showBackground = true)
    @Composable
    fun ImageNet() {
        ImageNetExample()
    }
}

*/
/*@Composable
fun MyBox(){
    Box(Modifier
        .size(100.dp)
        .padding(16.dp)
        .background(Color.Blue)
    )
}


@Preview(showBackground = true)
@Composable
fun ColumnShowCase(){
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,){
        Text("")
        MyBox()
        MyBox()
        MyBox()
        MyBox()
    }
}*/

/*
       @Preview(showBackground = true)
       @Composable
       fun TextSample() {
           var input = remember { mutableStateOf("") }
           Column(
               modifier = Modifier.fillMaxSize(),
               horizontalAlignment = Alignment.CenterHorizontally
           ) {
               Text(
                   "Maryam Develoer",
                   style = TextStyle.Default.copy(
                       color = Color.Blue,
                       fontSize = 30.sp,
                       fontStyle = FontStyle.Italic
                   )
               )
               Text(
                   "مریم برنامه نویس",
                   style = TextStyle.Default.copy(
                       color = Color.Red,
                       fontStyle = FontStyle.Italic,
                       textDirection = TextDirection.Rtl
                   )
               )
               BasicText("Hello word with my friend")


           }
       }*/

/*   @Composable
   fun ButtonExample() {
       var selectedButton by remember { mutableStateOf("") }
       Button(
           onClick = {},
           elevation = ButtonDefaults.buttonElevation(
               defaultElevation = 8.dp,
               pressedElevation = 12.dp,
               disabledElevation = 0.dp
           ),
           modifier = Modifier.fillMaxWidth().padding(8.dp)
       )
           {
               Text("Maryam")
           }
   }


   @Preview(showBackground = true)
   @Composable
   fun RunButton(){
       ButtonExample()
   }

*/


/*
    @Preview(showBackground = true)
    @Composable
    fun IconButton(){
        Button(onClick = {}, modifier = Modifier.fillMaxWidth()){

            Row {
                Icon(Icons.Filled.FavoriteBorder, contentDescription = null)
                Text("Like")
            }
        }
    }
*/
