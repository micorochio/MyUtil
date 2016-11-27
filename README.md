# MyUtil
我造的轮子

+ Java
    + random_string_util（随机字符串工具）
    + security(MD5工具)
    + captcha(验证码工具)
    
    
# random_string_util（随机字符串工具）
看源码，有注释
#security(MD5工具)
看源码，轻量级，有注释
# captcha(验证码工具)
CaptchaImageProducer +SpringMVC使用方法

```
    @RequestMapping("/captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageIO.setUseCache(false);

        HttpSession session = request.getSession();
        response.setHeader("Cache-Control", "n0-store,no-catch");
        response.setContentType("image/jpeg");

        //创建随机验证码
        String capStr = RandomUtil.getRandomStringFrom(4, RandomUtil.LOWER_CASE, RandomUtil.UPPER_CASE, RandomUtil.NUMBERS);
        //创建验证码图片
        BufferedImage bufferedImage = CaptchaImageProducer.getInstance().createCAPTCHAImage(capStr);
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(bufferedImage, "jpg", outputStream);

        session.setAttribute(CAPTCHA_KEY, capStr);
        session.setAttribute(CAPTCHA_DATE, LocalDateTime.now(ZoneId.of(ZoneId.SHORT_IDS.get("CTT"))));//CTT是上海时间
    }
    
     @RequestMapping("/register")
     public void registerAccount(HttpServletRequest request) {
         HttpSession session = request.getSession();
         System.out.println(session.getAttribute(CAPTCHA_KEY));
         System.out.println(session.getAttribute(CAPTCHA_DATE));
     }

```

可以到CaptchaImageProducer配置一些基本样式




