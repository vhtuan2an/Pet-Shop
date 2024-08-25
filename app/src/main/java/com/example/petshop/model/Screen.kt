package com.example.petshop.model

sealed class Screen(val route: String, val title: String) {
    data object HomeScreen : Screen(route = "home", title = "Trang chủ")
    data object NotificationScreen : Screen(route = "notification", title = "Thông báo")
    data object ShoppingCartScreen : Screen(route = "shopping_cart", title = "Giỏ hàng")
    data object ProfileScreen : Screen(route = "profile", title = "")
    data object EditProfileScreen : Screen(route = "edit_profile", title = "Sửa thông tin")
    data object CheckoutScreen : Screen(route = "check_out", title = "Thanh toán")
    data object ShipmentStateScreen1 : Screen(route = "shipment_state_1", title = "Đơn hàng")
    data object ShipmentStateScreen2 : Screen(route = "shipment_state_2", title = "Đơn hàng")
    data object LoadingCheckout : Screen(route = "loading_checkout", title = "")
    data object TransactionScreen : Screen(route = "transaction", title = "")
    data object FollowShipping :
        Screen(route = "follow_shipping/{orderId}", title = "Theo dõi đơn hàng") {
        fun createRoute(orderId: String) = "follow_shipping/$orderId"
    }

    data object SelectPayMethod :
        Screen(route = "select_pay_method", title = "Phương thức thanh toán")

    data object SelectVoucher : Screen(route = "select_voucher", title = "Voucher")
    data object ProductDetailScreen : Screen(route = "product_detail/{productId}", title = "") {
        fun createRoute(productId: String) = "product_detail/$productId"
    }

    data object LoginScreen : Screen(route = "login", title = "")
    data object RegisterScreen : Screen(route = "register", title = "")

    data object ChatScreen : Screen(route = "chat", title = "Chat")
    data object SearchScreen : Screen(route = "search/{query}", title = "Tìm kiếm") {
        fun createRoute(query: String) = "search/$query"
    }

    data object FavoriteProductScreen :
        Screen(route = "favorite_product", title = "Sản phẩm yêu thích")
}
