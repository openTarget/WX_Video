const app = getApp()

Page({
    data: {
      indicatorDots: true,
      autoplay: true,
      interval: 5000,
      duration: 1000,
      usernameplac: '请输入账号',
      passwordplac: '请输入密码',
      imgarray: [
        '../resource/images/backage.jpg',
        '../resource/images/backage.jpg',
        '../resource/images/backage.jpg'
      ]
    },
  doRegist (e) {
    var username = e.detail.value.username;
    var password = e.detail.value.password;
    if (username === null || username === ''){
      this.setData({
        usernameplac: '账号不能为空！'
      })
      wx.showToast({
        title: '用户名和密码不能为空！',
        icon: 'none',
        duration: 2000
      })
    } else if (password === null || password === ''){
      this.setData({
        passwordplac: '密码不能为空！'
      })
    } else {
      wx.showLoading({
        title: '请等待....',
      })
      wx.request({
        url: app.serverUrl +'/regist',
        method: "POST",
        data: {
          username: username,
          password: password
        },
        header: {
          'content-type': 'application/json' // 默认值
        },
        success: function (res) {
          var status = res.data.status;
          wx.hideLoading()
          if (status === 200 ){
            wx.showToast({
              title: '用户注册成功了~!!!',
              icon: 'none',
              duration: 2000
            })
            console.log(res.data)
          } else if (status === 500) {
            wx.showToast({
              title: res.data.msg,
              icon: 'none',
              duration: 2000
            })
          }
        }
      })
    }
  },
  goLoginPage() {
    console.log("返回登陆！")
    wx.navigateTo({
      url: '/pages/userLogin/login'
    })
  }
})