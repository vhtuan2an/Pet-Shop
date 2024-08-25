package com.example.petshop.data

import com.example.petshop.R
import com.example.petshop.model.ClothesProduct
import com.example.petshop.model.FoodProduct
import com.example.petshop.model.ToyProduct

val dummyProductData = listOf(
    FoodProduct(
        id = "FP001",
        name = "Thức ăn hạt mềm Zenith cho chó mèo",
        description = "Thơm ngon bổ dưỡng, dễ ăn, dễ tiêu hóa",
        detailDescription = "Với những bé cún, mèo mới tập ăn hạt, đang bị ốm, biếng ăn hoặc lớn tuổi, ba mẹ thường phải ngâm hạt hoặc nghiền nhỏ cho các bé dễ ăn. \n" +
                "\n" +
                "Giờ đây, đã có cách đơn giản hơn chính là thức ăn hạt mềm. Zenith dễ nhai, dễ tiêu hóa, đảm bảo đủ chất dinh dưỡng cho cún phát triển. Đặc biệt, có bổ sung lợi khuẩn, tăng sức đề kháng tự nhiên\n" +
                "\n" +
                "HẠT MỀM - Dễ tiêu hóa, Hấp thụ nhanh, Bé khỏe manh\n" +
                "Hạt mềm Zenith Small Breed không chứa thành phần là các loại đậu giá rẻ, bắp, lúa mì… nguyên nhân chính gây dị ứng và khó tiêu ở chó mèo\n" +
                "Sản phẩm giúp ức chế sự tăng trưởng của vi sinh vật đường ruột có hại và ngăn chặn tiêu chảy nhờ có bổ sung thêm bột dừa, chất xơ, các vitamin và khoáng chất.",
        image = R.drawable.fp001,
        price = 45000.0,
        oldPrice = 62000.0,
        star = 5.0,
        quantity = 1,
        tags = listOf("Được yêu thích", "Thức ăn", "Chó", "Mèo"),
    ),
    FoodProduct(
        id = "FP002",
        name = "PATE SIÊU THƠM NGON - Đảm bảo chất lượng 100%",
        description = "Cung cấp nguồn dinh dưỡng cho chó mèo - Đảm bảo an toàn vệ sinh",
        detailDescription = "PATE GAN BÍ ĐỎ\n" +
                "- ĐÓNG GÓI : Hộp nhựa 200g và 500g\n" +
                "- THÀNH PHẦN: 50% Thịt xay, 20% Gan gà, 20% bí đỏ, 10% heo xào\n" +
                "- BẢO QUẢN : Trong ngăn mát 2-3 ngày, ngăn đông 1 tháng\n" +
                "- CÁCH DÙNG : Ăn liền, ăn không hoặc trộn với cơm hoặc các loại thức ăn khác đều được\n" +
                "\uD83D\uDCA5Pate mình nấu số lượng ít mỗi ngày, pate nấu hàng tươi mình bảo quản ngăn mát nhiệt độ cao nhưng không làm đông cứng, pate mình đã làm chín rất kĩ và an toàn nên sau khi mua về là thú cưng nhà mình ăn liền được ngay nhé, không cần chế biến lại nha \uD83E\uDD70",
        image = R.drawable.fp002,
        price = 20000.0,
        oldPrice = 24000.0,
        star = 4.9,
        quantity = 1,
        tags = listOf("Được yêu thích", "Thức ăn", "Chó", "Mèo"),
    ),
    FoodProduct(
        id = "FP003",
        name = "Dr Kyan Predogen Sữa bột cho chó con, chó mang thai, kén ăn bổ sung dưỡng chất, chống tiêu chảy",
        description = "Sữa bột cho chó con, chó mang thai, kén ăn, chống tiêu chảy",
        detailDescription = "Sữa bột dành cho chó PREDOGEN được sản xuất theo công thức của WONDERLIFE PHARMA  (mỹ) là một thực phẩm bổ dưỡng cho chó yêu của bạn ngoài những thức ăn thông thường, giúp chó yêu cảm thấy ngon miệng hơn, bồi bổ cơ thể và cung cấp những thứ cần thiết để phát triển toàn diện.\n" +
                "\n" +
                "Thành Phần: \n" +
                "Sữa bột nguyên kem, Sữa bột gầy,Nondairy creamer, Maltodextrin,Sucrose,Whey protein concentrate,Hương dùng trong thực phẩm,Chất xơ Inulin, Lysine, Nano - Precipitated Calcium Carbonate,Vitamin C, Vitamin K1, Vitamin B6, Vitamin B1, VitaminB2, Vitamin D3, Vitamin\n" +
                "A, Vitamin B12, Vitamin Axit Pantothenic, Biotine, Axit Folic...\n" +
                "\n" +
                "Dinh dưỡng : \n" +
                "- Canxi nano và vitamin D : Kích thước siêu nhỏ  giúp hấp thụ tối ưu vào xương, giúp xương và rang chắc khỏe, đặc biệt không tạo ra sỏi thận như thức ăn thông thường.\n" +
                "- Vitamin A : Tốt cho mắt và ngăn biến chứng võng mạc.\n" +
                "- Biotine : Giúp cho da khỏe mạnh và bộ lông bóng mượt.\n" +
                "- Lnulin :chất xơ tự nhiên giúp hệ tiêu hóa khỏe mạnh.\n" +
                "- Lysine : Kích thích thèm ăn\n" +
                "- Folic acid : Hỗ trợ phát triển trí não.\n" +
                "\n" +
                " \n" +
                "\n" +
                "Hướng dẫn sử dụng:\n" +
                "\n" +
                "*Pha với nước ấm khoảng 40 - 50°C\n" +
                "\n" +
                "Chó con dưới 1 tháng tuổi: Hòa 1 muỗng sữa bột ( khoảng 5gr ) vào 10ml nước ấm, chia thành 4-6 lần, dung bình cho bú hoặc để mèo tự ăn hết trong ngày.\n" +
                "Chó con từ 1 – 2  tháng tuổi: Hòa  2 muỗng sữa bột (khoảng 10gr) với 20ml nước ấm, chia thành 3- 4 lần ăn trong ngày.\n" +
                "Chó trên 2 tháng tuổi: Cho ăn khoảng 2-3 lần/ ngày như bữa phụ xen kẽ với các bữa chính, mỗi lần cho ăn bằng cách hòa 2 -3 muỗng sữa bột (khoảng 10 - 15gr) với 20ml - 30ml nước ấm để tự ăn.\n" +
                "Chó đang ốm/ còi/ đang mang thai: cho ăn khoảng 3 lần/ ngày như bữa phụ xen kẽ với các bữa chính, mỗi lần cho ăn bằng cách hòa 2- 3 muỗng sữa bột (khoảng 10- 15gr) với 20- 30ml nước ấm để tự ăn.\n" +
                "\n" +
                "Bảo quản:\n" +
                "\n" +
                "- Nơi khô ráo thoáng mát.\n" +
                "- Sữa đã pha, bảo quản trong tủ lạnh được 24h.\n" +
                "\n" +
                "Giới thiệu Dr. Kyan :\n" +
                "\n" +
                "Các sản phẩm dành cho thú cưng của Dr. Kyan được tư vấn công thức và công nghệ sản xuất bởi Wonderlife Pharma – một trong những công ty phát triển công thức hàng đầu của Mỹ.\n" +
                "Dr. Kyan cung cấp không chỉ các sản phẩm tốt mà còn có chất lượng vượt trội cũng như đề cao việc cung cấp hàm lượng dinh dưỡng cân bằng cho thú cưng giúp chúng có khả năng hấp thu tốt, đảm bảo cơ thể dẻo dai và có sức đề kháng đầy đủ.",
        image = R.drawable.fp003,
        price = 41000.0,
        oldPrice = 55000.0,
        star = 4.9,
        quantity = 1,
        tags = listOf("Thức ăn", "Chó"),
    ),
    FoodProduct(
        id = "FP004",
        name = "Thức Ăn Hạt Royal Canin Poodle Adult Cho Chó Poodle Trưởng Thành",
        description = "Sản phẩm thức ăn dinh dưỡng dành riêng cho giống chó Poodle với hình dáng hạt được thiết kế đặc biệt dành riêng cho giống chó này. ",
        detailDescription = "ROYAL CANIN POODLE ADULT\n" +
                "\n" +
                "Sản phẩm thức ăn dinh dưỡng dành riêng cho giống chó Poodle với hình dáng hạt được thiết kế đặc biệt dành riêng cho giống chó này. Nhờ vào trình độ chuyên môn khoa học từ ROYAL CANIN và kinh nghiệm của các nhà nhân giống trên toàn thế giới, ROYAL CANIN POODLE được ra đời nhằm mang lại sự khác biệt\n" +
                "\n" +
                "Duy trì sức khoẻ của lông\n" +
                "Hình dạng hạt độc quyền giúp chó dễ dàng nhai\n" +
                "Hỗ trợ tiêu hóa khỏe mạnh\n" +
                "Hỗ trợ sức khỏe hệ thống miễn dịch\n" +
                "100% dinh dưỡng cân bằng và cân bằng\n" +
                "100% đảm bảo an toàn\n" +
                "ROYAL CANIN POODLE ADULT là sản phẩm dành cho giống chó Poodle trên 10 tháng tuổi.\n" +
                "\n" +
                "Hình dáng hạt\n" +
                "\n" +
                "Vì Poodle có mõm dài, thẳng và xương hàm nhỏ nên hình dáng hạt dành riêng cho giống chó này được thiết kế thích hợp để chúng dễ dàng nhai hơn.\n" +
                "\n" +
                "LỢI ÍCH\n" +
                "Thiết kế hạt đặc biệt: bảo vệ răng\n" +
                "Hình dáng hạt được thiết kế độc quyền qua nhiều nghiên cứu nhằm thích nghi với chiếc hàm nhỏ của Poodle, kích thích chúng nhai một cách dễ dàng. Ngoài ra, nhờ vào công thức đặc biệt giúp giảm thiểu quá trình hình thành cao răng nhờ vào tác dụng lọc thải Canxi.\n" +
                "\n" +
                "Bộ lông khỏe mạnh\n" +
                "Da và lông phản ánh tình trạng sức khoẻ của chó, chính vì vậy mà thức ăn thích hợp rất quan trọng trong việc duy trì làn da và bộ lông khỏe mạn. ROYAL CANIN POODLE JUNIOR chứa các chất dinh dưỡng giúp duy trì bộ lông của Poodle trở nên mượt mà với Axit béo Omega-3 (EPA & DHA). Ngoài ra, protein có trong hạt giúp hỗ trợ tăng trưởng lông cho Poodle.\n" +
                "\n" +
                "Tăng cường hệ miễn dịch\n" +
                "Poodle được biết đến với tuổi thọ dài. POODLE ADULT giúp hỗ trợ Poodle hệ miễn dịch ngay cả khi chúng trưởng thành, với việc chọn lọc các chất dinh dưỡng để duy trì sức khỏe và sức sống.\n" +
                "\n" +
                "Sức khỏe cơ bắp\n" +
                "Công thức này giúp duy trì hệ cơ của Poodle với hàm lượng protein thích hợp.\n" +
                "\n" +
                "THÀNH PHẦN\n" +
                "Nguyên liệu\n" +
                "Bắp, protein gia cầm, protein thực vật*, chất béo động vật, gạo, bột bắp, gluten bắp, protei động vật, củ cải đường, khoáng chất, dầu đậu nành, dầu cá, men, xơ thực vật, fructo-oligo-sacarit, dầu borage (0,1%), giáp xác thủy phân (nguồn glucosamine), chiết xuất cúc vạn thọ (nguồn lutein), sụn thủy phân (nguồn chondroitin).\n" +
                "\n" +
                "Phụ gia dinh dưỡng: Vitamin A, Vitamin D3, E1 (Sắt), E2 (I ốt), E4 (Đồng), E5 (Mangan), E6 (Kẽm), E8 (Selen) - Phụ gia kỹ thuật: Clinoptilolite - Phụ gia cảm quan: Chiết xuất trà xanh (nguồn polyphenols) - Chất chống oxi hóa.",
        image = R.drawable.fp004,
        price = 153000.0,
        oldPrice = 169000.0,
        star = 5.0,
        quantity = 1,
        tags = listOf("Thức ăn", "Chó", "Mèo"),
    ),
    FoodProduct(
        id = "FP005",
        name = "Thịt sấy 25 loại đa dạng dinh dưỡng, tăng cân tăng nọng, bổ sung chất xơ cho chó mèo",
        description = "Cung cấp lượng protein cao, đáp ứng được nhu cầu cần thịt của các boss, giúp tăng cường sự phát triển.",
        detailDescription = "Thịt sấy 25 loại đa dạng dinh dưỡng, tăng cân tăng nọng, bổ sung chất xơ, hỗ trợ tiêu hóa, dưỡng lông cho chó mèo\n" +
                "---> Sản phẩm được cân đủ lượng, không bao gồm bao bì và hút ẩm\n" +
                "\n" +
                "- Cung cấp lượng protein cao, đáp ứng được nhu cầu cần thịt của các boss, giúp tăng cường sự phát triển.\n" +
                "- Vì các bé thú cưng như chó mèo là động vật ăn thịt, cần rất nhiều chất dinh dưỡng không thể tự tổng hợp được như: axit amin, vitamin A, taurine, arginine,.. đặc biệt là mèo khó hấp thụ protein, nên cần đầu tư đạm động vật bằng cách cho dùng thêm thịt để đáp ứng nhu cầu dinh dưỡng của các bé, thức ăn đông khô hoàn toàn có thể đáp ứng các nhu cầu dinh dưỡng đó\n" +
                "- Kích thích sự thèm ăn, hạn chế kén ăn\n" +
                "- Dễ tiêu hoá\n" +
                "- Sử dụng tiện lợi nhanh gọn, không tốn quá nhiều thời gian của chủ nuôi. Chỉ cần lấy ra cho ăn trực tiếp\n" +
                "- Giữ nguyên hương vị, không chất bảo quản và chất phụ gia khác. \n" +
                "- Sản phẩm được mix nhiều loại khác nhau, đáp ứng đầy đủ nhu cầu dinh dưỡng cho các bé\n" +
                "- Sản phẩm bổ sung chất xơ, hổ trợ tiêu hóa, giúp hệ tiêu hóa bé khỏe mạnh\n" +
                "\n" +
                "* Quy trình thực hiện: Thịt tươi được đưa vào máy ở nhiệt độ -35 độ C. Sau đó, khử nước và khử trùng bằng công nghệ hút chân không. Giữ nguyên các chất dinh dưỡng của thịt mà bảo quản được rất lâu.\n" +
                "\n" +
                "* Hướng dẫn sử dụng:\n" +
                "- Cho bé ăn trực tiếp\n" +
                "- Xé nhỏ trộn với cơm hoặc với hạt\n" +
                "- Sử dụng cho thú cưng trên 3 tháng tuổi, có thể ngâm sữa hoặc nước cho mềm ra để bé mèo nhỏ có thể nhai và tiêu hóa dễ dàng hơn\n" +
                "\n" +
                "* Quy cách: sản phẩm được chiết và đóng trong túi zip ko bao gồm cân nặng bao bì và hút ẩm\n" +
                "* Bảo quản: Nơi thoáng mát, tránh ánh nắng trực tiếp, đóng kĩ túi sau khi sử dụng và sử dụng hết trong vòng 1-2 tháng vì sản phẩm không sử dụng chất bảo quản",
        image = R.drawable.fp005,
        price = 39000.0,
        oldPrice = 42000.0,
        star = 4.3,
        quantity = 1,
    ),
    FoodProduct(
        id = "FP006",
        name = "SỮA BỘT CAO CẤP DÙNG CHO CHÓ MÈO BIO MILK - SỮA CHÓ MÈO",
        description = "Sữa bột cao cấp Bio Milk dành cho chó mèo, giúp các bé tăng cân, bổ sung dinh dưỡng",
        detailDescription = "SỮA BỘT CAO CẤP CHO CHÓ MÈO BIO MILK\n" +
                " Sữa bột cao cấp Bio Milk dành cho chó mèo, giúp các bé tăng cân, bổ sung dinh dưỡng, rất dể tiêu hóa tương tự sữa mẹ, có tác dụng thay thế sữa mẹ trong trường hợp chó, mèo mất sữa, kém sữa hoặc thiếu sữa do bầy con quá đông, đồng thời là nguồn bổ sung chất dinh dưỡng cho chó mèo còi cọc, chậm lớn. Thay thế sữa mẹ trong khi chó mèo mẹ mất, chó mèo mẹ ít sữa, hoặc bầy con quá đông.\n" +
                "ĐỐI TƯỢNG SỬ DỤNG:\n" +
                "- Chó mèo sơ sinh dưới 2 tháng tuổi \n" +
                "- Chó mèo con từ 2 tháng tuổi trở lên \n" +
                "- Chó mèo còi cọc, chậm lớn hoặc đang bệnh \n" +
                "- Chó mèo biếng ăn, sau khi phẩu thuật, vận động nhiều\n" +
                "- Chó mèo con mất mẹ, bị bỏ rơi\n" +
                "\tHƯỚNG DẪN SỪ DỤNG\n" +
                "–\tChó, mèo con dưới 1 tháng tuổi: hòa 1 muỗng cafe (5g) sữa với 20ml nước ấm, dùng bình cho bú ngày 4-5 lần. \n" +
                "–\tChó, mèo còn từ 1-2 tháng tuổi: hòa 1 muỗng cafe (5g) sữa với 15ml nước ấm, dùng dĩa hoặc mang cho uống tự do ngày 3-4 lần.\n" +
                "–\tChó mèo trên 2 tháng tuổi: ngoài thức ăn thông thường, cho ăn thêm 1 muỗng cafe (5g) / kg thể trọng/ ngày, bằng cách trộn vào thức ăn hoặc hòa với nước ấm cho uống.\n" +
                "–     Chó, mèo còi cọc, chậm lớn hoặc đang mắc bệnh: ngoài thức ăn thông thường, cho ăn thêm 2 muỗng cafe (10g) /kg thể trọng/ ngày, bằng cách trộn vào thức ăn hoặc hòa với nước ấm cho uống.\n" +
                "\tNGUYÊN LIỆU\n" +
                "Sữa bột, men tiêu hóa, các vitamin (A, E, B12,B1,D3), khoáng, chất điện giải và đường lactose…..\n" +
                "\n" +
                "\tBẢO QUẢN: Bảo quản nơi khô mát, tránh ánh sáng trực tiếp\n" +
                "\n" +
                "\tHẠN SỬ DỤNG: In trên bao bì sản phẩm \n" +
                "\n" +
                "\tTHƯƠNG HIỆU\n" +
                "Bio-Pharmachemie là thương hiệu sản xuất sản phẩm trong chăn nuôi hoạt động từ năm 1996 tại Việt Nam. Công ty sản xuất đạt tiêu chuẩn WHO GMP với những máy móc, trang thiết bị hiện đại, quy trình công nghệ tiên tiến cùng với đội ngũ chuyên gia giỏi, giàu kinh nghiệm trong ngành chăn nuôi.\n",
        image = R.drawable.fp006,
        price = 32000.0,
        oldPrice = 43000.0,
        star = 4.9,
        quantity = 1,
        tags = listOf("Thức ăn", "Sữa", "Chó", "Mèo"),
    ),

    // ===================== Toy =====================

    ToyProduct(
        id = "TP001",
        name = "Đồ Chơi Dây Thừng Bện Hình Xương Tập Nhai Gặm Cho Thú Cưng Chó Mèo",
        description = "Nó có thể thỏa mãn ham muốn nhai của chúng",
        detailDescription = "- Chất liệu: Cotton. \n" +
                " - Sản phẩm cho vật nuôi ở mọi lứa tuổi, đặc biệt tốt cho chó cỡ trung bình và nhỏ. \n" +
                " - Nó có thể thỏa mãn ham muốn nhai của chúng. \n" +
                " - Hình dạng: Xương.",
        image = R.drawable.tf001,
        price = 15000.0,
        oldPrice = 15000.0,
        star = 4.1,
        quantity = 1,
        tags = listOf("Được yêu thích", "Đồ chơi", "Chó"),
    ),
    ToyProduct(
        id = "TP002",
        name = "Đồ chơi cho mèo Chuột bông chạy dây cót đồ thú cưng",
        description = " Sản phẩm được thiết kế hình chuột như 1 con chuột nhỏ",
        detailDescription = "- Kích thước: khoảng 16cm (cả đuôi)\n" +
                "- Sản phẩm được thiết kế hình chuột như 1 con chuột nhỏ. Chỉ cần xoáy dây cót ở thân chuột, chuột được lên cót và bắt đầu chạy đuôi ngoáy thật tít khi bạn thả tay ra. Shopee.vn/grandstore\n" +
                "- Có thể Dùng để chơi đùa cùng mèo cưng đồng thời giúp chúng ngoan ngoãn hơn, không phá phách khi bạn vắng nhà.\n" +
                "- Màu sắc ngộ nghĩnh đáng yêu, thu hút mèo cưng nhà bạn chơi đùa cùng bóng lật đật, kích thích bản năng phát triển, tăng cường khả năng vận động hoạt bát, nhanh nhạy. \n" +
                "- Thiết kế dây cót giúp bạn sử dụng lâu dài dễ dàng mà vẫn tiết kiệm.\n" +
                "- Kiểu dáng chuột bằng vải lông dễ thương\n" +
                "- Chất liệu: Nhựa + Vải lông\n" +
                "✅ Để biết thêm thông tin về sản phẩm và dịch vụ, quý khách hàng có thể Chat với Shop hoặc liên hệ trực tiếp qua số Hotline được cập nhật trong phần giới thiệu shop (nhấn XEM SHOP để xem thêm).\n" +
                "✅ Sản phẩm không có phân loại cụ thể, Shop sẽ gửi ngẫu nhiên và chỉ ưu tiên lựa chọn nếu được.\n" +
                "✅ Khi đặt hàng quý khách vui lòng kiểm tra đầy đủ thông tin đơn hàng (tên, số điện thoại, địa chỉ) để tránh những nhầm lẫn ",
        image = R.drawable.tf002,
        price = 15000.0,
        oldPrice = 21000.0,
        star = 4.7,
        quantity = 1,
        tags = listOf("Được yêu thích", "Đồ chơi", "Mèo"),
    ),
    ToyProduct(
        id = "TP003",
        name = "Đồ chơi cho chó mèo chất liệu cao su hình Núm ti cho thú cưng đồ chơi cho mèo",
        description = "Nó giúp chúng giảm căng thẳng và tăng cường sự linh hoạt",
        detailDescription = "Sản phẩm được làm từ chất liệu cao su an toàn, không độc hại cho thú cưng. \n" +
                "Nó giúp chúng giảm căng thẳng và tăng cường sự linh hoạt. \n" +
                "Sản phẩm có hình dáng núm ti, giúp chúng cảm thấy thoải mái như đang bú sữa mẹ. \n" +
                "Sản phẩm có nhiều màu sắc, giúp chúng thích thú hơn khi chơi. \n" +
                "Sản phẩm phù hợp với mọi loại thú cưng như chó, mèo, thỏ, v.v. \n" +
                "Kích thước: 5 x 5 x 5 cm. \n" +
                "Màu sắc: ngẫu nhiên.",
        image = R.drawable.tf003,
        price = 9600.0,
        oldPrice = 19000.0,
        star = 5.0,
        quantity = 1,
        tags = listOf("Đồ chơi", "Chó", "Mèo"),

        ),
    ToyProduct(
        id = "TP004",
        name = "Lông gà trêu mèo đàn hồi, đồ chơi cho mèo",
        description = "Lông gà trêu mèo đàn hồi - Đồ chơi cho mèo",
        detailDescription = "\uD83D\uDD2F Đặc điểm\n" +
                "✅ Thiết kế đơn giản, chắc chắn.\n" +
                "✅ Có gắn lò xo đàn hồi bằng thép không gỉ\n" +
                "✅ Đầu còn lại có lông vũ, chuông để tạo sự thu hút cho mèo\n" +
                "✅ Có đế hít gắn cố định tạo khu vui chơi riêng cho mèo\n" +
                "\n" +
                "\uD83D\uDD2F CÔNG DỤNG:\n" +
                "✅Đồ chơi cần câu mèo giúp huấn luyện mèo bản năng tự nhiên, nhanh nhẹn, linh hoạt trong hành động. Tạo mối quan hệ thân thiết gắn bó giữ người nuôi và mèo trong lúc chơi đùa cùng nhau. Giúp bé mèo giải trí, giảm stress cho mèo.\n",
        image = R.drawable.tf004,
        price = 20140.0,
        oldPrice = 20140.0,
        star = 4.4,
        quantity = 1,
        tags = listOf("Được yêu thích", "Đồ chơi", "Mèo"),
    ),
    ToyProduct(
        id = "TP005",
        name = "Ổ Đệm, Nệm, Nhà Êm Ái Có Vành Tai Kèm Cục Bông Tròn Đồ Chơi Dành Cho Thú Cưng Chó Mèo",
        description = "Ổ đệm cute đáng yêu dành cho thú cưng của bạn",
        detailDescription = "Kích thước: 40x44x34cm( Ngang*Sâu*Cao)\n" +
                "Chất liệu: Vải Cotton + Bông PP 3D\n" +
                "MÔ TẢ SẢN PHẨM\n" +
                "\uD83C\uDF35 Được làm từ vải dày cao cấp rất êm ái.\n" +
                "\uD83C\uDF35 Ổ đệm thiết kế có vành tai, màu sắc trang nhã rất thích hợp cho cún dưới 7kg.\n" +
                "\uD83C\uDF35 Nệm rất mềm mịn và thoải mái,tạo cảm giác ấm áp giúp thú cưng có giấc ngủ ngon.\n" +
                "\uD83C\uDF35 Thiết kế có khóa kéo có thể tháo rời và giặt được bằng tay hoặc máy.",
        image = R.drawable.tf005,
        price = 151000.0,
        oldPrice = 151000.0,
        star = 3.9,
        quantity = 1,
        tags = listOf("Giường", "Chó", "Mèo"),
    ),

    // ===================== Clothes =====================

    ClothesProduct(
        id = "CP001",
        name = "Áo Tết Cho Chó Mèo Quần Áo Thú Cưng Tết Thời Trang Cao Cấp",
        description = "Tết Việt Quần Áo Cao Cấp Nhất Cho Thú Cưng",
        detailDescription = "\uD83D\uDCA5 Chi tiết sản phẩm:\n" +
                "- Kích thước: XS, S, M, L, XL, XXL.\n" +
                "- Màu sắc: nhiều màu gấm\n" +
                "- Form áo sang trọng hoàng thượng đẳng cấp\n" +
                "- Phong cách: dễ thương, mát mẻ, ấp áp, năng động.\n" +
                "- Chất liệu: Lụa Satin Chiết Giang trứ danh của thế giới về gấm & lụa.\n" +
                "- Form dáng chuẩn cho hầu hết các bé\n" +
                "- Sản phẩm dễ thương phù hợp tất cả thú cưng chó mèo.\n" +
                "- Nhận sản phẩm cam kết ưng ý 100%. Các bé mặc vào sẽ rất đẹp.\n" +
                "\n" +
                "\uD83D\uDCA5 Mô tả sản phẩm:\n" +
                "- Dòng sản phẩm thiết kế riêng cho Tết Nguyên Đán ấn tượng sâu sắc phong cách Trung cổ\n" +
                "- Một sản phẩm được thiết kế với chất liệu tốt nhất chưa ai dám dùng trong ngành\n" +
                "- May kỹ từng đường nét sợi chỉ. Nếu như Hermès, Gucci, Louis Vuitton.. hàng hiệu của người thì đây là bộ đồ cho các bé thú cưng hàng hiệu như thế.\n" +
                "- Được nhà sản xuất thiết kế làm rất kỹ và cầu kỳ từng chi tiết làm nên chiếc áo rất đẹp bởi sự công phu cùng sự kết hợp màu sắc đỏ, phong cách Triều đình xưa và ý nghĩa của từng chiếc áo là điểm đặc biệt nhất dòng sản phẩm cao cấp này.\n" +
                "- Hàng mới sản xuất đầu năm 2024 nên đây là sản phẩm phù hợp cho các ba mẹ mua cho các bé mặc dịp năm mới sắp đến.\n" +
                "- Thiết kế lần đầu tiên & duy nhất có mặt tại Việt Nam\n" +
                "- Dòng sản phẩm premium của Hiha pets\n" +
                "\n" +
                "\uD83C\uDF50 LƯU Ý:\n" +
                "\n" +
                "Về số liệu trên chỉ mang tính chất tương đối. Size áo còn phụ thuộc vào giống cún, miu độ dày lông và sai số cân nặng hoặc chiều dài lưng do dụng cụ đo của mỗi người khác nhau, quần áo có thể có sai số 2-3cm.\n" +
                "\n" +
                "Ba mẹ có thể inbox cho shop để được tư vấn size áo nha.\n" +
                "\n" +
                "\uD83D\uDD14 Khi đo SIZE các bé ba mẹ nhớ đo chính xác KHÔNG TRỪ HAO.\n" +
                "\n" +
                "\uD83C\uDF50 CHÍNH SÁCH ĐỔI SIZE:\n" +
                "\n" +
                "- Nếu bạn muốn đổi size, vui lòng gửi lại hàng ra cho chúng mình, chúng mình sẽ gửi lại size muốn đổi cho bạn, bạn thanh toán phí ship 2 chiều. Giải quyết trong 7 ngày kể từ ngày nhận được hàng.\n" +
                "- 100% hàng mới và chất lượng cao.",
        image = R.drawable.cp001,
        price = 235000.0,
        oldPrice = 285000.0,
        star = 5.0,
        quantity = 1,
        tags = listOf("Được yêu thích", "Quần áo", "Chó"),
    ),
    ClothesProduct(
        id = "CP002",
        name = "ÁO SIÊU NHÂN CHÓ CHÓ MÈO",
        description = "Đẹp và ngầu cho boss",
        detailDescription = "ÁO SIÊU NHÂN CHÓ CHÓ MÈO\n" +
                "SUPERDOG CỰC CUTE VÀ NGẦU CHO BOSS\n" +
                "Gồm các size: S (1-2kg), M (2-3kg), L (3-4kg), XL (4-5.5kg), XXL (5-6kg)\n" +
                "Chất liệu: thun sợi tổng hợp siêu mát",
        image = R.drawable.cp002,
        price = 119000.0,
        oldPrice = 119000.0,
        star = 4.4,
        quantity = 1,
        tags = listOf("Được yêu thích", "Quần áo", "Chó", "Mèo"),
    ),
    ClothesProduct(
        id = "CP003",
        name = "Áo con tôm cho chó lớn (12 - 26kg)",
        description = "Áo độc lạ, dễ thương cho boss",
        detailDescription = "Áo con tôm \uD83E\uDD90 siêu độc cho Boss (từ 10 - 26kg)\n" +
                "\n" +
                "Áo \uD83E\uDD90 được thiết kế độc, lạ, giúp Boss nổi bật mọi lúc mọi nơi. Dù là diện đi chụp ảnh\uD83D\uDCF8, hay đi chơi\uD83D\uDE95, diện trong ngày Tết\uD83C\uDF3C đều phù hợp ạ\n" +
                "Chất vải nỉ dạ, lên form nhìn rất đứng dáng, lại còn có độ bóng, lấp lánh nhẹ, vô cùng nổi bật.",
        image = R.drawable.cp003,
        price = 260000.0,
        oldPrice = 260000.0,
        star = 5.0,
        quantity = 1,
        tags = listOf("Quần áo", "Chó"),
    ),
)