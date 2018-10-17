const app = getApp()

Page({
  data: {
  },
  doLogin (e) {
    var username = e.detail.value.username;
    var password = e.detail.value.password;
    console.log(username);
    if (username.length === 0 || password.length === 0){
      wx.showToast({
        title: '账号和密码不能为空！',
        icon: 'none',
        duration: 2000
      })
    }else{
      wx.showLoading({
        title: '请等待....',
      });
      wx.request({
        url: app.serverUrl + '/login',
        method: 'POST',
        data:{
          username: username,
          password: password
        },
        header: {
          'content-type': 'application/json' // 默认值
        },
        success: function (res) {
          console.log(res.data)
          wx.hideLoading()
          var status = res.data.status;
          if (status === 200) {
            wx.showToast({
              title: '登录成功了~!!!',
              icon: 'none',
              duration: 2000
            })
            app.userInfo = res.data.data
            wx.navigateTo({
              url: '/pages/mine/mine',
            })
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
  goRegistPage () {
    wx.navigateTo({
      url: '/pages/userRegist/regist',
    })
  }
})