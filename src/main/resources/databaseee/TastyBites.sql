DROP DATABASE TastyBites
GO
CREATE DATABASE	TastyBites
GO
USE master
GO
USE TastyBites
GO
-------------------------Create data-------------------------
/****** Object:  Table [dbo].[Accounts]    Script Date: 5/30/2023 3:44:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Accounts](
	[Username] [nvarchar](50) NOT NULL,
	[Password] [nvarchar](200) NOT NULL,
	[Fullname] [nvarchar](50) NOT NULL,
	[Email] [nvarchar](50) NOT NULL,
	[Photo] [nvarchar](50)  NULL,
	[Phone] [nvarchar](12) NULL,
 CONSTRAINT [PK_Customers] PRIMARY KEY CLUSTERED 
(
	[Username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Authorities]    Script Date: 5/30/2023 3:44:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Authorities](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Username] [nvarchar](50) NOT NULL,
	[RoleId] [nvarchar](10) NOT NULL,
 CONSTRAINT [PK_UserRoles] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Categories]    Script Date: 5/30/2023 3:44:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Categories](
	[Id] [varchar](25) NOT NULL,
	[Name] [nvarchar](50) NOT NULL,
	[Image] [nvarchar](255) NOT NULL,
 CONSTRAINT [PK_Categories] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[OrderDetails]    Script Date: 5/30/2023 3:44:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OrderDetails](
	[Id] [bigint] IDENTITY(1,1) NOT NULL,
	[OrderId] [bigint] NOT NULL,
	[ProductId] [int] NOT NULL,
	[Price] [float] NOT NULL,
	[Quantity] [int] NOT NULL,
 CONSTRAINT [PK_OrderDetails] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Orders]    Script Date: 5/30/2023 3:44:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Orders](
	[Id] [bigint] IDENTITY(1,1) NOT NULL,
	[Username] [nvarchar](50) NOT NULL,
	[CreateDate] [datetime] NOT NULL,
	[Address] [nvarchar](100) NOT NULL,
	[DiscountId] [int] NULL,
	[PaypalOrderId] varchar(32) NULL,
	[PaypalOrderStatus] varchar(32) NULL,
	[Status] nvarchar(250) NOT NULL,
 CONSTRAINT [PK_Orders] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Discount]    Script Date: 5/30/2023 3:44:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Discount](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](50) NOT NULL,
	[Code] [varchar](10) NOT NULL,
	[Price] [float] NOT NULL,
	[Quality] [int] NOT NULL,
	[ApplyDay] [date] NOT NULL,
	[Expiration] [date] NOT NULL,
	[CreateDate] [date] NOT NULL,
 CONSTRAINT [PK_Discount] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Favorites]    Script Date: 5/30/2023 3:44:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Favourites](
	[Favourited] [int] IDENTITY(1,1) NOT NULL,
	[Username] [nvarchar](50) NULL,
	[ProductId] [int] NULL,
	[ViewedDate] [datetime] NOT NULL default getDate(),
	[IsLiked] [bit] NOT NULL default 0,
	[LikedDate] [datetime] Null
 CONSTRAINT [PK_Favorites] PRIMARY KEY CLUSTERED 
(
	[Favourited] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
UPDATE Favourites
SET IsLiked = 1
WHERE Favourited = 7

SET IDENTITY_INSERT [dbo].[Favourites] ON 

INSERT [dbo].[Favourites] ([Favourited], [Username], [ProductId], [ViewedDate], [IsLiked], [LikedDate]) VALUES (1,'longnt',1,getDate(),0,null)

SET IDENTITY_INSERT [dbo].[Favourites] OFF

GO
/****** Object:  Table [dbo].[Products]    Script Date: 5/30/20238/16/2022 3:44:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Products](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](255) NOT NULL,
	[Image] [nvarchar](255) NOT NULL,
	[Price] [float] NOT NULL,
	[ProductQuantity] int null,
	[Description] [nvarchar](max) NOT NULL,
	[CreateDate] [date] NOT NULL,
	[Available] [bit] NOT NULL,
	[Star] [int] NULL,
	[CategoryId] [varchar](25) NOT NULL,
 CONSTRAINT [PK_Products] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Roles]    Script Date: 5/30/2023 3:44:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Roles](
	[Id] [nvarchar](10) NOT NULL,
	[Name] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_Roles] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

CREATE TABLE Visitors(
	id int IDENTITY(1,1) primary key,
	[views] bigint
)

CREATE TABLE [dbo].[Reviews](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[ProductId] [int] NOT NULL,
	[Username] [nvarchar](50) NOT NULL,
	[Rating] [int] NOT NULL,
	[Comment] [nvarchar](max) NULL,
	[ReviewDate] [datetime] NOT NULL DEFAULT GETDATE(),
	CONSTRAINT [PK_Reviews] PRIMARY KEY CLUSTERED 
	(
		[Id] ASC
	) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

-------------------------Insert data-------------------------

----------Table Accounts----------
INSERT [dbo].[Accounts] ([Username], [Password], [Fullname], [Email], [Phone], [Photo]) VALUES (N'longnt', N'$2a$10$iretZDJynC2hAh0hnemcFOkaFa4QlJSOGYg2YHaeTJjApKS.TJF3S', N'Ngô Thiên Long', N'longnt@gmail.com', '0358132152', N'longnt.jpg')
INSERT [dbo].[Accounts] ([Username], [Password], [Fullname], [Email], [Phone], [Photo]) VALUES (N'taivn', N'$2a$10$iretZDJynC2hAh0hnemcFOkaFa4QlJSOGYg2YHaeTJjApKS.TJF3S', N'Huỳnh Văn Tài', N'taihvt@gmail.com', '0354123102', N'anh3.jpg')
INSERT [dbo].[Accounts] ([Username], [Password], [Fullname], [Email], [Phone], [Photo]) VALUES (N'kiettl', N'$2a$10$iretZDJynC2hAh0hnemcFOkaFa4QlJSOGYg2YHaeTJjApKS.TJF3S', N'Trần Lê Tú Kiệt', N'kiettl@gmail', '0284151215', N'anh4.jpg')
INSERT [dbo].[Accounts] ([Username], [Password], [Fullname], [Email], [Phone], [Photo]) VALUES (N'thu', N'$2a$10$iretZDJynC2hAh0hnemcFOkaFa4QlJSOGYg2YHaeTJjApKS.TJF3S', N'Nguyễn Ngọc Anh Thư', N'thu@gmail.com', '0352212552', N'anh1.jpg')
INSERT [dbo].[Accounts] ([Username], [Password], [Fullname], [Email], [Phone], [Photo]) VALUES (N'Thuy', N'$2a$10$iretZDJynC2hAh0hnemcFOkaFa4QlJSOGYg2YHaeTJjApKS.TJF3S', N'Lê Thị Thủy', N'thuy@gmail.com', '0351002153', N'anh5.jpg')
INSERT [dbo].[Accounts] ([Username], [Password], [Fullname], [Email], [Phone], [Photo]) VALUES (N'khoa', N'$2a$10$iretZDJynC2hAh0hnemcFOkaFa4QlJSOGYg2YHaeTJjApKS.TJF3S', N'Lê Đăng Khoa', N'khoa@gmail.com', '0353810254', N'anh3.jpg')
INSERT [dbo].[Accounts] ([Username], [Password], [Fullname], [Email], [Phone], [Photo]) VALUES (N'phat', N'$2a$10$iretZDJynC2hAh0hnemcFOkaFa4QlJSOGYg2YHaeTJjApKS.TJF3S', N'Nguyễn Đức Phát', N'phat@gmail.com', '0205151236', N'anh6.jpg')
INSERT [dbo].[Accounts] ([Username], [Password], [Fullname], [Email], [Phone], [Photo]) VALUES (N'thanh', N'$2a$10$iretZDJynC2hAh0hnemcFOkaFa4QlJSOGYg2YHaeTJjApKS.TJF3S', N'Phan Nguyễn Trung Thành', N'thanh@gmail', '0248120147', N'anh8.jpg')
INSERT [dbo].[Accounts] ([Username], [Password], [Fullname], [Email], [Phone], [Photo]) VALUES (N'tien', N'$2a$10$iretZDJynC2hAh0hnemcFOkaFa4QlJSOGYg2YHaeTJjApKS.TJF3S', N'Lê Việt Tiến', N'tien@gmail.com', '0336481248', N'anh6.jpg')
INSERT [dbo].[Accounts] ([Username], [Password], [Fullname], [Email], [Phone], [Photo]) VALUES (N'admin', N'$2a$10$iretZDJynC2hAh0hnemcFOkaFa4QlJSOGYg2YHaeTJjApKS.TJF3S', N'Vũ Phi Ưng Vương', N'vuong@gmail.com', '0305412054', N'anh3.jpg')


----------Table Authorities----------
SET IDENTITY_INSERT [dbo].[Authorities] ON 

INSERT [dbo].[Authorities] ([Id], [Username], [RoleId]) VALUES (1, N'longnt', N'DIRE')
INSERT [dbo].[Authorities] ([Id], [Username], [RoleId]) VALUES (2, N'taivn', N'DIRE')
INSERT [dbo].[Authorities] ([Id], [Username], [RoleId]) VALUES (3, N'kiettl', N'CUST')
INSERT [dbo].[Authorities] ([Id], [Username], [RoleId]) VALUES (4, N'thuy', N'CUST')
INSERT [dbo].[Authorities] ([Id], [Username], [RoleId]) VALUES (5, N'thu', N'STAF')
INSERT [dbo].[Authorities] ([Id], [Username], [RoleId]) VALUES (6, N'tien', N'CUST')
INSERT [dbo].[Authorities] ([Id], [Username], [RoleId]) VALUES (7, N'khoa', N'STAF')
INSERT [dbo].[Authorities] ([Id], [Username], [RoleId]) VALUES (8, N'admin', N'DIRE')
INSERT [dbo].[Authorities] ([Id], [Username], [RoleId]) VALUES (9, N'thanh', N'CUST')
INSERT [dbo].[Authorities] ([Id], [Username], [RoleId]) VALUES (10, N'phat', N'STAF')

SET IDENTITY_INSERT [dbo].[Authorities] OFF

----------Table Categories----------

INSERT [dbo].[Categories] ([Id], [Name], [Image]) VALUES ('Thit-Tuoi-Song', N'Thịt Tươi Sống', N'thit.jpg')
INSERT [dbo].[Categories] ([Id], [Name], [Image]) VALUES ('Rau-Cu', N'Rau Củ', N'rau-cu.jpg')
INSERT [dbo].[Categories] ([Id], [Name], [Image]) VALUES ('Hai-San', N'Hải Sản', N'hai-san.jpg')
INSERT [dbo].[Categories] ([Id], [Name], [Image]) VALUES ('Thuc-An-Nhanh', N'Thức Ăn Nhanh', N'thuc-an-nhanh.jpg')
INSERT [dbo].[Categories] ([Id], [Name], [Image]) VALUES ('Do-Uong', N'Đồ Uống', N'do-uong.jpg')
INSERT [dbo].[Categories] ([Id], [Name], [Image]) VALUES ('Do-Trang-Mieng', N'Đồ Tráng Miệng', N'do-trang-mieng.jpg')
INSERT [dbo].[Categories] ([Id], [Name], [Image]) VALUES ('Gia-Vi', N'Gia Vị', N'gia-vi.jpg')
INSERT [dbo].[Categories] ([Id], [Name], [Image]) VALUES ('Trai-Cay', N'Trái Cây', N'trai-cay.jpg')
INSERT [dbo].[Categories] ([Id], [Name], [Image]) VALUES ('Do-Chay', N'Đồ Chay', N'do-chay.jpg'	)

----------Table Discount----------
SET IDENTITY_INSERT [dbo].[Discount] ON 

INSERT [dbo].[Discount] ([Id], [Name], [Code], [Price], [Quality], [ApplyDay], [Expiration], [CreateDate]) VALUES (1, N'Giảm giá nhân ngày 20/10', N'QTPN2010', 10000, 10, CAST(N'2023-07-14' AS Date), CAST(N'2023-08-14' AS Date), CAST(N'2023-10-18' AS Date))
INSERT [dbo].[Discount] ([Id], [Name], [Code], [Price], [Quality], [ApplyDay], [Expiration], [CreateDate]) VALUES (2, N'Giảm giá hóa đơn trên 500 ngàn', N'GGHD500', 50000, 15, CAST(N'2023-6-24' AS Date), CAST(N'2023-7-6' AS Date), CAST(N'2023-6-20' AS Date))

SET IDENTITY_INSERT [dbo].[Discount] OFF

----------Table Products----------

SET IDENTITY_INSERT [dbo].[Products] ON
--------Danh Muc Rau Cu--------
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (1, N'Bông cải san hô nhập khẩu ở Úc 300g', N'bongcai.jpg', 110000, 10, 
		N'Bông cải san hô là loại rau có hình dáng độc đáo, màu tươi sáng, hương vị nhẹ. Đầu hoa nhỏ gọn được bao quanh bởi những chiếc lá, thân cây tạo thành hình xoắn ốc, tất cả tạo nên một loại rau hấp dẫn trên thị trường.  Bông cải san hô là loại hàm lượng dinh dưỡng cao, giúp bổ sung các dưỡng chất cần thiết cho cơ thể, cải thiện thị lực, tim mạch, giảm nguy cơ ung thư',
		'2023-07-06', 1, 4, 'Rau-Cu')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available,  [Star], CategoryId)
		VALUES (2, N'Đậu Hà Lan Đà Lạt 200g', N'dauhalan.jpg', 39900, 10,
		N'Đậu Hà Lan (hay đậu Hòa Lan, đậu pơ-tí poa, tên khoa học: Pisum sativum) là loại đậu hạt tròn thuộc Chi Đậu Hà Lan, dùng làm rau ăn. Hạt đậu Hà Lan được dùng làm rau ăn ở các dạng tươi, đông lạnh, đóng hộp, hoặc khô. Trong ẩm thực Việt Nam, quả đậu Hà Lan non còn được dùng nguyên quả cho các món xào hoặc canh. Đậu Hà Lan có chứa các sắc tố màu xanh lá cây Chlorophyllin, một chất liên quan đến chất diệp lục có hình dạng phân tử cho phép nó kết hợp với các hóa chất gây ung thư trong cơ thể. Đậu Hà Lan là nguồn cung cấp chất xơ dồi dào, cứ nửa cốc đậu Hà Lan bạn sẽ nhận được khoảng 4g chất xơ.',
		'2023-07-06', 1, 4, 'Rau-Cu')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (3, N'Hành tím Vĩnh Châu 300g', N'hanhtimvc.jpg', 27500, 10,
		N'Hành tím chứa 415-1.917mg chất chống oxy hóa, trong khi đó hành hoa chỉ có 270-1.187mg. Một trong những thành phần trong hành tím là quercetin, có tác dụng ngăn ngừa lão hóa. Ngoài ra, hành tím cũng cũng được chứng minh giúp hạn chế nguy cơ mắc ung thư thực quản, tử cung và vòm họng,.. Hành tím còn bổ máu vì chứa nhiều chất sắt. Trong khi đó, sắt là vi chất tham gia tích cực vào quá trình tạo máu, vận chuyển oxy từ phổi đến các tế bào.',
		'2023-07-03', 1, 4, 'Rau-Cu')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (4, N'Măng tây xanh bó 350g', N'mangtayxanh.jpg', 59900, 10,
		N'Măng tây ngoài là một loại thực phẩm giòn ngon còn là thực phẩm có nhiều dược tính. Có hàm lượng cao các dưỡng chất như chất xơ, đạm, glucid, các vitammin K, C, A, pyridoxine (B6), riboflavin (B2), thiamin (B1), acid folid, các chất khoáng cần thiết cho cơ thể con người như: kali, magnê, canxi, sắt, kẽm… măng tây mang lại rất nhiều lợi ích cho sức khỏe con người. Được trồng theo quy trình đạt tiêu chuẩn tại các nông trại lớn và được chăm sóc tỉ mỉ để thu được những sản phẩm chất lượng nhất.',
		GETDATE(), 1, 5, 'Rau-Cu')
		
--------Danh Muc Hai San--------
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (5, N'Cá Sặc Rằn Trứng Ướp Muối 300g', N'casacran.jpg', 46000, 10,
		N'Măng tây ngoài là một loại thực phẩm giòn ngon còn là thực phẩm có nhiều dược tính. Có hàm lượng cao các dưỡng chất như chất xơ, đạm, glucid, các vitammin K, C, A, pyridoxine (B6), riboflavin (B2), thiamin (B1), acid folid, các chất khoáng cần thiết cho cơ thể con người như: kali, magnê, canxi, sắt, kẽm… măng tây mang lại rất nhiều lợi ích cho sức khỏe con người. Được trồng theo quy trình đạt tiêu chuẩn tại các nông trại lớn và được chăm sóc tỉ mỉ để thu được những sản phẩm chất lượng nhất.',
		'2023-07-06', 1, 4, 'Hai-San')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (6, N'Tôm hấp Choice L Size 31-50 400g', N'tomhapchoice.jpg', 142500, 10,
		N'Tôm hấp Choice L được làm từ những con tôm tươi ngon tự nhiên, được chọn lọc và chế biến kỹ lưỡng mang đến cho sản phẩm chất lượng. Tôm đông lạnh Choice L không sử dụng các chất bảo quản độc hại, đảm bảo không làm ảnh hưởng đến sức khỏe người tiêu dùng. Sản phẩm được chế biến dưới dạng đông lạnh, mang đến sự tiện lợi cho bạn trong việc bảo quản và sử dụng.',
		GETDATE(), 1, 5, 'Hai-San')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (7, N'Khô cá dứa một nắng nguyên con Cần Giờ 900g', N'cahongocean.jpg', 75000, 10,
		N'Cá Hồng Đại Dương Phi Lê Hồn Việt tươi ngon, luôn được bán với lớp da được giữ nguyên, điều này sẽ giúp giữ miếng thịt cá còn nguyên và bạn xem da để chắc chắn rằng đó là cá hồng. Thịt cá hồng có kết cấu chắc chắn và hương vị thơm ngon, ngọt và mọng nước rất hấp dẫn, cá hồng được xếp vào hàng cao lương mỹ vị và có thể được chế biến thành nhiều món khác nhau như nướng, áp chảo, hấp, chiên giòn hay thậm chí là món hầm. Cá hồng đại dương chứa sắt, canxi và protein và ít calo, chất béo bão hòa và cholesterol cực kỳ phù hợp cho những bữa ăn “healthy”, chế độ ăn eat clean hoặc keto.',
		'2023-07-03', 1, 4, 'Hai-San')

--------Danh Muc Trai Cay--------
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (8, N'Nho đen không hạt Úc túi 500g', N'nhodenuc.jpg', 89500, 10,
		N'Nho đen không hạt Úc size 18-20mm có quả đen bóng thuôn dài, thịt quả giòn và ngọt. Với hàm lượng lớn các chất dinh dưỡng như Canxi, kali, photpho, sắt, vitamin B1, B2, B6, C và các loại axit amin,… nho rất tốt cho việc bồi bổ cơ thể, giúp cơ thể khỏe mạnh, miễn dịch tốt. Việc ăn nho thường xuyên giúp cơ thể khỏe mạnh, giúp nhanh phục hồi đối với những người bị thiếu máu kinh niên, những bệnh nhân bị huyết áp cao, viêm phế quản, gout, viêm dạ dày và táo bón.',
		GETDATE(), 1, 4, 'Trai-Cay')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (9, N'Táo Tessa xuất xứ Hà Lan 1000g', N'taotessa.jpg', 109000, 10,
		N'Táo Tessa có màu đỏ đậm, vị ngọt, thơm, giòn, mang đến hương vị thơm ngon cho người thưởng thức. Táo Tessa thích hợp là loại trái cây ăn tráng miệng dinh dưỡng cho cả gia đình hoặc làm quà biếu cũng rất ý nghĩa và hợp lý. Táo tươi ngon, chất lượng, không bị dập. Cũng như nhiều loại táo khác, táo Tessa có hàm lượng chất xơ cao, tập trung ở phần vỏ táo.Táo Tessa có chứa nhiều vitamin C, vitamin A, vitamin E, các vitamin nhóm B, cũng như khoáng chất đồng, canxi,..Trong 100g táo có khoảng 52 Kcal.',
		'2023-07-03', 1, 5, 'Trai-Cay')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (10, N'Quýt Ai Cập 1000g', N'quytaicap.jpg', 65900, 10,
		N'Quýt Ai Cập quả tròn vừa ăn, vỏ màu vàng cam, dễ lột, mùi thơm dịu, vị ngọt đậm. Quýt chứa nhiều chất xơ, vitamin A, B, canxi, Magnesium, sắt, đồng, I ốt… có tác dụng hỗ trợ tiêu hóa, giảm viêm, giúp ngăn ngừa chứng chuột rút, chắc xương, bổ mắt. Giàu kali giúp duy trì các khớp xương, huyết áp và các động mạch khỏe mạnh. Hỗ trợ tiêu hóa, giảm viêm, giúp ngăn ngừa chứng chuột rút. ',
		'2023-07-06', 1,  4,'Trai-Cay')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (11, N'Sầu riêng hạt lép 9 Phẻ 205g', N'saurienglep.jpg', 248000, 10,
		N'Sầu Riêng Hạt Lép 9 Phẻ là sầu riêng cắt chín già: trái còn trên cây, chín già tầm 85% đến 95% sẽ được thợ canh cắt xuống để trung bình 4-5 ngày sẽ chín hoàn toàn tự nhiên mà không phải có bất cứ tác động nào.Sản phẩm được đóng gói hút chân không và đóng hộp giấy đẹp mắt, thích hợp làm quà tặng cho bạn bè, người thân và gia đình.Sầu riêng là món ăn vặt ưa thích phù hợp với cả trẻ nhỏ và người lớn.',
		'2023-07-03', 1, 5, 'Trai-Cay')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (12, N'Mít giống Thái 9 Phẻ 405g', N'mitthai.jpg', 79000, 10,
		N'Mít giống Thái 9 Phẻ cắt già và để chín tự nhiên nên đảm bảo độ ngọt và thơm của mít.Giống mít Thái cho quả quanh năm, hết lứa quả chín đến lứa non. Thịt quả vàng đậm, ít xơ, ráo nước, vị ngọt đậm và thơm dịu.Sản phẩm được đóng gói hút chân không, thích hợp làm quà tặng cho bạn bè, người thân và gia đình.',
		GETDATE(), 1, 4, 'Trai-Cay')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (13, N'Xoài giống Đài Loan 1600g', N'xoaidailoan.jpg', 30239, 10,
		N'Xoài giống Đài Loan là giống xoài ngoại nhập với nhiều ưu điểm nổi trội so với xoài ta truyền thống như: trái to, vỏ trơn nhẵn, khi chín quả có mùi thơm mát và vị ngọt lịm.Quả xoài Đài Loan xanh cùi dầy, thịt quả chắc, ít xơ, hạt mỏng, ăn ngọt đậm. Khi ăn xanh vẫn ngọt.Xoài mang lại nhiều lợi ích cho sức khỏe. Nhiều nghiên cứu đã chứng minh xoài có khả năng làm giảm nguy cơ béo phì, bệnh tim,...',
		'2023-07-03', 1, 5, 'Trai-Cay')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (14, N'Dâu tây giống Nhật & Mâm xôi 250g', N'dautaynhat.jpg', 129000, 10,
		N'Dâu tây giống Nhật & mâm xôi 250g là dòng berry mix đẹp mắt, sang trọng, mang đến sự tiện dụng 2 trong 1 cho những tín đồ yêu thích hai loại berry fruit này.Dâu tây và quả mâm xôi đều được trồng trong những nhà màng công nghệ cao tại Đà Lạt, đặt tiêu chuẩn Vietgap sạch và an toàn cho người sử dụng.Dâu tây là một loại trái cây phổ biến chứa hàm lượng cao các vitamin, chất chống oxy hóa và các thành phần dinh dưỡng quan trọng khác.',
		GETDATE(), 1, 4, 'Trai-Cay')

--------Danh Muc Thit Tuoi Song--------
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (15, N'Thịt heo xay Choice L 500g', N'thitheochoice.jpg', 44950, 10,
		N'Thịt heo xay là lựa chọn số 1 giúp cho bữa ăn nhanh gọn và đơn giản. Thịt heo chứa nhiều vitamin D giúp phát triển xương.Ngoài ra, thịt heo còn có vitamin K có tác dụng giống vitamin D chống loãng xương, hàm lượng vitamin nhóm B tương đối cao giúp thúc đẩy chuyển hóa Carb, Fat, Protein thành Glucose dành cho các hoạt động cơ bắp và chỉ số cân bằng về vitamin, khoáng chất trong cơ thể.Heo được nuôi theo công nghệ tiên tiến, đảm bảo vệ sinh, an toàn thực phẩm.',
		GETDATE(), 1, 5, 'Thit-Tuoi-Song')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (16, N'Sườn cừu cắt kiểu Pháp KiaOra 300g', N'suoncuuphap.jpg', 279000, 10,
		N'Sườn Cừu nhập khẩu New Zealand được cắt kiểu Pháp là phần thịt được pha từ sườn cừu đã được lọc toàn bộ các phần gân, mỡ và màng gân.Sau khi lọc bỏ mỡ và gân thì sườn cừu cắt kiểu Pháp là phần thịt ba nhất: ngon nhất, đẹp nhất và chất lượng nhất.Sườn Cừu Cắt Kiểu Pháp New Zealand 300G là sự lựa chọn hoàn hảo cho món áp chảo, đút lò hoặc nướng trên lửa (BBQ).',
		'2023-07-03', 1, 5, 'Thit-Tuoi-Song')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (17, N'Đùi tỏi gà Ba Huân 500g', N'duigabahuan.jpg', 44900, 10,
		N'Đùi Tỏi Gà Ba Huân Khay là phần thịt được pha lóc từ đùi của những con gà đạt chuẩn sạch, phần thịt này nạc nhiều,dai, ngọt.Thịt gà là thực phẩm giàu chất dinh dưỡng: hàm lượng Protein cao và đa dạng, giàu Photpho-chất có lợi cho răng và xương, nhiều khoáng chất Selenium rất cần thiết trong việc trao đổi chất trong cơ thể, giúp tăng cường hệ miễn dịch trong cơ thể. Sản phẩm được chăn nuôi theo phương thức hữu cơ đạt tiêu chuẩn Vietgap.',
		GETDATE(), 1, 4, 'Thit-Tuoi-Song')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (18, N'Ba Chỉ Bò Mỹ Nướng SumoBBQ Icook 300g', N'bachibomy.jpg', 117000, 10,
		N'Ba Chỉ Bò Mỹ Nướng SumoBBQ Icook 300g với những lát thịt bò Mỹ được thái mỏng bắt mắt và vô cùng thu hút. Từng lát thịt với tỉ lệ nạc, mỡ đạt chuẩn giúp bạn dễ dàng chế biến thành các món ăn yêu thích, ngoài ra còn giúp lát thịt đạt chuẩn vị thơm ngon khi thưởng thức.Từng miếng thịt thơm ngon sẽ làm hài lòng các tín đồ yêu thích các món làm từ ba chỉ bò Mỹ. Thịt bò đạt tiêu chuẩn Mỹ (USDA CHOICE) giúp bạn yên tâm về chất lượng cũng như xuất xứ của thịt. Thịt bò được sản xuất trên dây chuyền công nghệ hiện đại, được đóng gói kỹ càng, tiện dụng giúp bạn dễ dàng bảo quản và mang di chuyển.',
		'2023-07-03', 1, 4, 'Thit-Tuoi-Song')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (19, N'Sườn Non Heo G Kitchen 500g', N'suonnonheo.jpg', 179000, 10,
		N'Sườn non G Kitchen là phần thịt rất được yêu thích với phần thịt mềm, kết hợp cân đối với phần mỡ khiến thịt không bị khô. Đặc biệt, phần sụn vừa mềm vừa giòn sừn sựt mang lại cảm giác thích thú khi ăn. Heo được nuôi theo công nghệ tiên tiến, quy trình lấy thịt khép kín, đảm bảo an toàn vệ sinh thực phẩm. Sản phẩm được đóng khay tiện lợi, vệ sinh khi đến với người tiêu dùng.',
		'2023-07-06', 1, 5, 'Thit-Tuoi-Song')

--------Danh Muc Gia Vi--------
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (20, N'Dầu Ăn Cooking Nakydaco Chai 1L', N'dauannakydaco.jpg', 54500, 10,
		N'Dầu Ăn Cooking Nakydaco Chai 1L hay còn gọi là dầu ăn con két, từ khâu nguyên liệu tới chế biến đều diễn ra khép kín dưới sự giám sát nghiêm ngặt. Dầu thực vật Cooking Oil Nakydaco 1 lít được chiết xuất từ các loại dầu tự nhiên khác nhau, không chứa hóa chất, chất bảo quản, an toàn cho sức khỏe người dùng. Thành phần: Dầu Palm Olein, dầu đậu nành tinh luyện, chất nhũ hóa (475), vitamin A. Ưu điểm: Bổ sung năng lượng và vitamin A, E tự nhiên tốt cho cơ thể. Hướng dẫn sử dụng: Dùng để chế biến các món ăn như chiên, xào, trộn salad, làm bánh, sốt trứng, nấu món chay.',
		GETDATE(), 1, 4, 'Gia-Vi')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (21, N'Dầu Mè Meizan Nguyên Chất Chai 250ML', N'daumemeizan.jpg', 66000, 10,
		N'Dầu Mè Meizan Nguyên Chất được tinh tế từ các hạt mè nguyên chất đã được tuyển chọn kỹ lưỡng, chứa hàm lượng dưỡng chất dồi dào giúp chống oxi hóa và ức chế các tác động xấu của các gốc tự do, tạo nên kháng thể cho cơ thể chống lại quá trình thoái lão hóa.Quy trình sản xuất theo tiêu chuẩn quốc tế, đảm bảo chất lượng và vệ sinh an toàn thực phẩm, giúp loại bỏ hoàn toàn tạp chất nên rất tốt cho sức khỏe; dầu có màu nâu hổ phách, độ sánh chuẩn mực và hương thơm tinh khiết giúp làm tăng thêm độ hấp dẫn của món ăn.',
		'2023-07-03', 1, 5, 'Gia-Vi')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (22, N'Tương Ớt Hàn Quốc Bibigo Cay Ngọt 260g', N'tuongothan.jpg', 25000, 10,
		N'Tương Ớt Hàn Quốc BiBiGo Cay Ngọt 260G một sự khác biệt đầy hấp dẫn mới khi được làm từ hỗn hợp ớt và tỏi lên men phối trộn thêm các loại gia vị như tempe bột mì, bột ớt, muối, hành tây, đường, tỏi,…tạo nên một loại chili sauce đỏ sệt rất bắt mắt và có vị thơm từ tỏi, cay ấm lan tỏa của ớt cùng một chút vị dịu ngọt vừa phải. Thích hợp: dùng để chấm trực tiếp với các món như khoai tây chiên, gà rán, bánh gạo lắc,...hay dùng như gia vị để ướp, chế biến đa dạng các món ăn hoặc mix cùng một số loại sốt, tương khác như mayonnaise, tương cà để tạo ra hỗn hợp sốt chấm hấp dẫn, thơm ngon hơn. ',
		GETDATE(), 1, 4, 'Gia-Vi')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (23, N'Nước Tương Phú Sĩ Chai 240ml', N'nuoctuongphusi.jpg', 10200, 10,
		N'Nước tương Phú Sĩ sử dụng kiage - được lên men tự nhiên trên 3 tháng theo công nghệ truyền thống Nhật Bản, tạo nên gia vị chấm tuyệt hảo, mang đến vị ngon đậm đà cho mọi món rau.Thành phần: Nước, muối, đạm thực vật, đường, chiết xuất từ hạt đậu nành và gạo lên men tự nhiên (kiage), chất điều vị, phẩm màu, chất điều chỉnh độ acid, hương nước tương tổng hợp, chất tạo ngọt.Hướng dẫn sử dụng: Dùng để chấm, kho, xào... Chay mặn đều dùng được. Bảo quản ở nơi khô thoáng, tránh ánh nắng trực tiếp.',
		'2023-07-03', 1, 4, 'Gia-Vi')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (24, N'Muối Ớt Tây Ninh Choice L 145g', N'muoiottayninh.jpg', 23000, 10,
		N'Muối Ớt Tây Ninh Choice L 145g với vị muối đậm đà, vị ớt nồng cay được chế biến theo công thức đặc biệt, phù hợp với tiêu chuẩn vệ sinh Châu Âu làm tăng khẩu vị cho từng món ngon. Muối được sử dụng đa dạng trong chế biến món ăn, như làm gia vị ướp thịt, cá, rau củ, các món nướng, làm món chấm trái cây,... giúp bạn tiết kiệm thời gian. Sản phẩm được sản xuất trên quy trình hiện đại kết hợp với công thức gia truyền, đạt tiêu chuẩn chất lượng vệ sinh an toàn thực phẩm. Sản phẩm được làm từ các thành phần tự nhiên được lựa chọn kỹ lưỡng, an toàn cho sức khỏe, mang đến sự an tâm cho người dùng.',
		GETDATE(), 1, 5, 'Gia-Vi')

--------Đồ Tráng Miệng--------
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (25, N'Kem tươi trái cây 500ml', N'kemtuoitc500.jpg', 35000, 10,
		N'Kem tươi trái cây 500ml với hương vị tươi ngon từ các loại trái cây tươi như dâu, việt quất, và cam, tạo nên một trải nghiệm thú vị và ngọt ngào cho bạn.',
		GETDATE(), 1, 5, 'Do-Trang-Mieng')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (26, N'Bánh flan trứng caramel', N'banhflan.jpg', 25000, 10,
		N'Bánh flan trứng caramel là một món tráng miệng thơm ngon và béo ngậy, với lớp caramel đường đen ngọt ngào trên cùng. Món này rất phổ biến và thường được thưởng thức sau bữa ăn chính.',
		'2023-07-03', 1, 4, 'Do-Trang-Mieng')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (27, N'Chè trôi nước', N'chetroinuoc.jpg', 20000, 10,
		N'Chè trôi nước là một món tráng miệng truyền thống của Việt Nam. Bạn sẽ thấy hấp dẫn với lớp vỏ mỏng mềm, bên trong là nhân đậu xanh thơm ngon, được tẩm ướp trong một chén nước đường ngọt ngào.',
		GETDATE(), 1, 4, 'Do-Trang-Mieng')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (28, N'Bánh flan matcha', N'banhflanmatcha.jpg', 28000, 10,
		N'Bánh flan matcha là một phiên bản đặc biệt của bánh flan với hương vị matcha độc đáo. Món tráng miệng này kết hợp giữa vị béo ngậy của kem và vị đắng nhẹ của matcha, tạo nên một hương vị tuyệt vời.',
		'2023-07-03', 1, 5, 'Do-Trang-Mieng')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (29, N'Kem chuối', N'kemchuoi.jpg', 22000, 10,
		N'Kem chuối là một món tráng miệng ngon lành với hương vị thơm ngon của chuối chín. Bạn sẽ cảm nhận được sự tươi mát và ngọt ngào khi thưởng thức món này.',
		GETDATE(), 1, 4, 'Do-Trang-Mieng')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (30, N'Bánh khoai mì hấp', N'banhkhoaimihap.jpg', 22000, 10,
		N'Bánh khoai mì hấp là một món tráng miệng truyền thống của Việt Nam. Bánh có vị ngọt nhẹ, hương thơm của khoai mì, và có kết cấu mềm mịn.',
		'2023-07-03', 1, 5, 'Do-Trang-Mieng')

--------Thức Ăn Nhanh--------
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (31, N'Bánh mỳ sandwich thịt gà', N'banhmi-ga.jpg', 35000, 10,
		N'Bánh mỳ sandwich thịt gà là món ăn nhanh phổ biến với lớp thịt gà thơm ngon, được kết hợp với các loại rau tươi và gia vị đặc trưng. Món này thích hợp cho bữa ăn nhẹ hoặc khi bạn đang trong hối hả và muốn có một bữa ăn nhanh.',
		GETDATE(), 1, 5, 'Thuc-An-Nhanh')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (32, N'Pizza hải sản', N'pizza-haisan.jpg', 45000, 10,
		N'Pizza hải sản là món ăn nhanh phổ biến với vị ngon của hải sản tươi ngon như tôm, cá, mực và các loại hải sản khác, được trang trí trên lớp bột mì và phủ lên một lớp sốt cà chua đậm đà. Món này thích hợp cho bữa ăn nhanh và những buổi tiệc nhỏ.',
		'2023-07-03', 1, 5, 'Thuc-An-Nhanh')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (33, N'Bánh mỳ que chà bông', N'banhmi-que-chabong.jpg', 25000, 10,
		N'Bánh mỳ que chà bông là một món ăn nhanh ngon miệng. Bánh mỳ được xé thành từng lát mỏng, chà bông mềm mịn được đặt lên trên và thêm gia vị như sốt mayonnaise hoặc nước mắm, tạo nên một món ăn hấp dẫn.',
		GETDATE(), 1, 4, 'Thuc-An-Nhanh') 
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (34, N'Mì xào hải sản', N'mi-xao-hai-san.jpg', 38000, 10,
		N'Mì xào hải sản là món ăn nhanh phổ biến và thơm ngon. Mì được xào cùng với hải sản tươi ngon như tôm, cá, mực và các loại rau củ tạo nên một món ăn bổ dưỡng và hấp dẫn.',
		'2023-07-03', 1, 5, 'Thuc-An-Nhanh')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (35, N'Bánh gạo cuốn chả giò', N'banh-gao-cuon-cha-gio.jpg', 28000, 10,
		N'Bánh gạo cuốn chả giò là món ăn nhanh truyền thống của Việt Nam. Bánh được làm từ gạo, cuốn chả giò và thêm các loại rau sống. Món ăn này có vị giòn ngon và thích hợp để thưởng thức trong các buổi tiệc nhỏ hoặc làm bữa ăn nhẹ.',
		'2023-07-03', 1, 4, 'Thuc-An-Nhanh')

--------Đồ Uống--------
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (36, N'Nước ngọt Coca-Cola', N'nuoc-ngot-coca-cola.jpg', 15000, 10,
		N'Nước ngọt Coca-Cola là một đồ uống đóng chai phổ biến và được yêu thích trên toàn thế giới. Với hương vị ngọt ngào và sảng khoái, Coca-Cola là lựa chọn hàng đầu cho những người thích thưởng thức nước ngọt.',
		'2023-07-03', 1, 5, 'Do-Uong')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (37, N'Nước trái cây Nutriboost', N'nuoc-trai-cay-nutriboost.jpg', 20000, 10,
		N'Nước trái cây Nutriboost là một đồ uống đóng chai giàu dinh dưỡng, được làm từ các loại trái cây tươi ngon. Với hương vị tự nhiên và cung cấp nhiều vitamin, Nutriboost là sự lựa chọn tuyệt vời cho sức khỏe.',
		GETDATE(), 1, 5, 'Do-Uong')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (38, N'Nước suối Lavie', N'nuoc-suoi-lavie.jpg', 10000, 10,
		N'Nước suối Lavie là một đồ uống đóng chai từ nguồn nước tự nhiên trong sạch. Với khẩu phần khoáng chất cân đối, Lavie mang đến sự tươi mát và thỏa mãn nhu cầu uống nước hàng ngày.',
		'2023-07-03', 1, 4, 'Do-Uong')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (39, N'Nước tăng lực Red Bull', N'nuoc-tang-luc-red-bull.jpg', 30000, 10,
		N'Nước tăng lực Red Bull là một đồ uống đóng chai giúp tăng cường năng lượng và cải thiện hiệu suất. Với hỗn hợp các thành phần tiên tiến, Red Bull là nguồn cung cấp năng lượng cho những người cần sự cảnh tỉnh và tăng độ tập trung.',
		'2023-07-03', 1, 5, 'Do-Uong')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (40, N'Nước trà xanh Lipton', N'nuoc-tra-xanh-lipton.jpg', 18000, 10,
		N'Nước trà xanh Lipton là một đồ uống đóng chai được làm từ trà xanh tự nhiên. Với hương vị tươi mát và chứa nhiều chất chống oxy hóa, Lipton là lựa chọn phổ biến cho những người yêu thích trà.',
		GETDATE(), 1, 4, 'Do-Uong')

--------Đồ Chay--------
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (41, N'Chả giò chay', N'cha-gio-chay.jpg', 25000, 10,
		N'Chả giò chay là một món ăn đồ chay truyền thống. Với nhân chay đậm đà và vỏ giòn tan, chả giò chay là một sự lựa chọn tuyệt vời cho những người ăn chay.',
		GETDATE(), 1, 4, 'Do-Chay')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (42, N'Bánh mì chay', N'banh-mi-chay.jpg', 15000, 10,
		N'Bánh mì chay là một món ăn chay phổ biến. Với vỏ bánh mềm mịn và nhân chay đa dạng, bánh mì chay là một sự lựa chọn hấp dẫn cho những người ăn chay.',
		'2023-07-03', 1, 5, 'Do-Chay')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (43, N'Bún chay', N'bun-chay.jpg', 20000, 10,
		N'Bún chay là một món ăn chay truyền thống. Với bún mềm và các loại rau, đậu hũ chay, bún chay là một món ăn nhẹ nhàng và thơm ngon.',
		'2023-07-03', 1, 4, 'Do-Chay')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (44, N'Súp chay', N'sup-chay.jpg', 18000, 10,
		N'Súp chay là một món ăn chay phổ biến và bổ dưỡng. Với hương vị đậm đà và nhiều loại rau củ, súp chay là một món ăn trọn vẹn cho những người ăn chay.',
		'2023-07-03', 1, 4, 'Do-Chay')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (45, N'Gỏi cuốn chay', N'goi-cuon-chay.jpg', 18000, 10,
		N'Gỏi cuốn chay là một món ăn chay phổ biến và tươi mát. Với các loại rau, đậu phụ chay và bánh tráng, gỏi cuốn chay là một sự lựa chọn hấp dẫn cho những người ăn chay.',
		GETDATE(), 1, 5, 'Do-Chay')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (46, N'Bánh chay', N'banh-chay.jpg', 15000, 10,
		N'Bánh chay là một món ăn chay truyền thống. Với hương vị thơm ngon và nhân chay đa dạng, bánh chay là một món ăn yêu thích của những người ăn chay.',
		'2023-07-03', 1, 5, 'Do-Chay')
INSERT INTO Products(Id, Name, Image, Price, [ProductQuantity], [Description], CreateDate, Available, [Star], CategoryId)
		VALUES (47, N'Chè hạt sen chay', N'che-hat-sen-chay.jpg', 25000, 10,
		N'Chè hạt sen chay là một loại đồ uống chay ngon và mát lạnh. Với hương vị đặc trưng của sen và hạt chè, chè hạt sen chay là một sự lựa chọn thú vị cho những người ăn chay.',
		GETDATE(), 1, 5, 'Do-Chay')

SET IDENTITY_INSERT [dbo].[Products] OFF
----------Table Roles----------

INSERT [dbo].[Roles] ([Id], [Name]) VALUES (N'CUST', N'Customers')
INSERT [dbo].[Roles] ([Id], [Name]) VALUES (N'DIRE', N'Directors')
INSERT [dbo].[Roles] ([Id], [Name]) VALUES (N'STAF', N'Staffs')

----------Table Visitors----------
SET IDENTITY_INSERT [dbo].[Visitors] ON

INSERT [dbo].[Visitors] ([id], [views]) VALUES (1,0)

SET IDENTITY_INSERT [dbo].[Visitors] OFF
-------------------------Table Relationship-------------------------

ALTER TABLE [dbo].[Accounts] ADD  CONSTRAINT [DF_Customers_Photo]  DEFAULT (N'Photo.gif') FOR [Photo]
GO
ALTER TABLE [dbo].[OrderDetails] ADD  CONSTRAINT [DF_Order_Details_UnitPrice]  DEFAULT ((0)) FOR [Price]
GO
ALTER TABLE [dbo].[OrderDetails] ADD  CONSTRAINT [DF_Order_Details_Quantity]  DEFAULT ((1)) FOR [Quantity]
GO
ALTER TABLE [dbo].[Orders] ADD  CONSTRAINT [DF_Orders_OrderDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
ALTER TABLE [dbo].[Products] ADD  CONSTRAINT [DF_Products_Image]  DEFAULT (N'Product.gif') FOR [Image]
GO
ALTER TABLE [dbo].[Products] ADD  CONSTRAINT [DF_Products_UnitPrice]  DEFAULT ((0)) FOR [Price]
GO
ALTER TABLE [dbo].[Products] ADD  CONSTRAINT [DF_Products_ProductDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
ALTER TABLE [dbo].[Products] ADD  CONSTRAINT [DF_Products_Available]  DEFAULT ((1)) FOR [Available]
GO
ALTER TABLE [dbo].[Authorities]  WITH CHECK ADD  CONSTRAINT [FK_UserRoles_Roles] FOREIGN KEY([RoleId])
REFERENCES [dbo].[Roles] ([Id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Authorities] CHECK CONSTRAINT [FK_UserRoles_Roles]
GO
ALTER TABLE [dbo].[Authorities]  WITH CHECK ADD  CONSTRAINT [FK_UserRoles_Users] FOREIGN KEY([Username])
REFERENCES [dbo].[Accounts] ([Username])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Authorities] CHECK CONSTRAINT [FK_UserRoles_Users]
GO
ALTER TABLE [dbo].[OrderDetails]  WITH CHECK ADD  CONSTRAINT [FK_OrderDetails_Orders] FOREIGN KEY([OrderId])
REFERENCES [dbo].[Orders] ([Id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[OrderDetails] CHECK CONSTRAINT [FK_OrderDetails_Orders]
GO
ALTER TABLE [dbo].[OrderDetails]  WITH CHECK ADD  CONSTRAINT [FK_OrderDetails_Products] FOREIGN KEY([ProductId])
REFERENCES [dbo].[Products] ([Id])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[OrderDetails] CHECK CONSTRAINT [FK_OrderDetails_Products]
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD  CONSTRAINT [FK_Orders_Customers] FOREIGN KEY([Username])
REFERENCES [dbo].[Accounts] ([Username]) 					    
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Orders] CHECK CONSTRAINT [FK_Orders_Customers]
GO
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD  CONSTRAINT [FK_Orders_Discount] FOREIGN KEY([DiscountId])
REFERENCES [dbo].[Discount] ([Id]) 					    
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Orders] CHECK CONSTRAINT [FK_Orders_Discount]
GO
GO
ALTER TABLE [dbo].[Favourites]  WITH CHECK ADD  CONSTRAINT [FK_Favourites_Customers] FOREIGN KEY([Username])
REFERENCES [dbo].[Accounts] ([Username])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Favourites] CHECK CONSTRAINT [FK_Favourites_Customers]
GO
GO
ALTER TABLE [dbo].[Favourites]  WITH CHECK ADD  CONSTRAINT [FK_Favourites_Products] FOREIGN KEY([ProductId])
REFERENCES [dbo].[Products] ([Id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Favourites] CHECK CONSTRAINT [FK_Favourites_Products]
GO
ALTER TABLE [dbo].[Products]  WITH CHECK ADD  CONSTRAINT [FK3ess0s7i9qs6sim1pf9kxhkpq] FOREIGN KEY([CategoryId])
REFERENCES [dbo].[Categories] ([Id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Products] CHECK CONSTRAINT [FK3ess0s7i9qs6sim1pf9kxhkpq]
GO

ALTER TABLE [dbo].[Reviews] WITH CHECK ADD  CONSTRAINT [FK_Reviews_Products] FOREIGN KEY ([ProductId]) REFERENCES [dbo].[Products] ([Id])
Go

ALTER TABLE [dbo].[Reviews] WITH CHECK ADD CONSTRAINT [FK_Reviews_Accounts] FOREIGN KEY ([Username]) REFERENCES [dbo].[Accounts] ([Username])
Go

ALTER TABLE Accounts
ADD auth_provider VARCHAR(255);

ALTER TABLE Accounts
DROP CONSTRAINT DF_Customers_Photo;

ALTER TABLE Accounts
ALTER COLUMN Photo VARCHAR(255) NULL;

ALTER TABLE [dbo].[Orders]
ADD CONSTRAINT DF_Orders_Status DEFAULT N'Đợi xác nhận' FOR [Status];



SELECT SUM(TotalPriceAfterDiscount) as FinalTotal
FROM (
    SELECT d.Price,
           COALESCE(SUM(od.Price * od.Quantity) - COALESCE(d.Price, 0), 0) + 15000 as TotalPriceAfterDiscount
    FROM Orders o
    INNER JOIN OrderDetails od ON o.Id = od.OrderId 
    LEFT JOIN Discount d ON o.DiscountId = d.Id
    WHERE o.Status = N'Đã giao'
    GROUP BY d.Price
) Subquery;
