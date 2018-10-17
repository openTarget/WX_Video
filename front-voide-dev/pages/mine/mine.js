const app = getApp()

Page({
  data: {
    faceUrl: "../resource/images/noneface.png"
  },

  onLoad: function (params) {
    var me = this
    var user = app.userInfo
    console.log(user.id)
    wx.showLoading({
      title: '加载中···',
    })
    wx.request({
      url: app.serverUrl + '/user/query?userId=' + user.id,
      method: 'POST',
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        console.log(res.data)
        var status = res.data.status
        console.log(status)
        if (status === 200) {
          var userInfo = res.data.data
          var faceUrl = '../ resource / images / noneface.png'
          if(userInfo.faceImage != null && userInfo.faceImage != '' && userInfo.faceImage != undefined){
            faceUrl = app.serverUrl + userInfo.faceImage
          }
          me.setData({
            faceUrl: faceUrl,
            fansCounts: userInfo.fansCounts,
            followCounts: userInfo.followCounts,
            receiveLikeCounts: userInfo.receiveLikeCounts,
            nickname: userInfo.nickname
          })
          wx.hideLoading({})
        }
      }
    })
  },
  logout () {
    var user = app.userInfo
    console.log(user.id)
    wx.request({
      url: app.serverUrl + '/logout?userId='+user.id,
      method: 'GET',
      header: {
        'content-type': 'application/json' // 默认值
      },
      success: function (res) {
        console.log(res.data)
        var status = res.data.status;
        if (status === 200) {
          wx.showToast({
            title: '登录成功了~!!!',
            icon: 'none',
            duration: 2000
          })
          app.userInfo = null
          wx.navigateTo({
            url: '/pages/userLogin/login',
          })
        }
      }
    })
  },
  changeFace () {
    var me = this
    var user = app.userInfo
    wx.chooseImage({
      count: 1, // 上传的数量
      sizeType: ['compressed'],
      sourceType: ['album', 'camera'],
      success (res) {
        var tempFilePaths = res.tempFilePaths;
        console.log(tempFilePaths);

        wx.showLoading({
          
          title: '上传中···',
        })
        var serverUrl = app.serverUrl
        wx.uploadFile({
          url: serverUrl + '/user/uplodeFace?userId='+user.id,
          filePath: tempFilePaths[0],
          name: 'file',
          header: {
            'content-type': 'application/json' // 默认值
          },
          success(res) {
            const data = JSON.parse(res.data)
            console.log(data)
            if (data.status == 200){
              wx.showToast({
                title: '上传成功···',
                icon: 'success'
              })

              var imageUrl = data.data.faceImage
              console.log("=================")
              console.log(imageUrl)
              console.log("=================")
              me.setData({
                faceUrl: serverUrl + imageUrl
              })
            } else if (res.data.status == 500){
              wx.showToast({
                title: res.data.msg,
              })
            }
          }
        })
      }
    })
  },
  uploadVideo () {
    wx.chooseVideo({
      sourceType: ['album', 'camera'],
      success(res) {
        console.log('res====' )
        console.log(res )        
        var duration = res.duration
        console.log('duration' + duration)
        var tmpHeight = res.height
        var tmpWidth = res.width
        var tmpVideoUrl = res.tempFilePath
        var tmpConverUrl = res.thumbTempFilePath

        if (duration > 11) {
          wx.showToast({
            title: '上传的视频不能大于10秒···',
            icon: "none",
            duration: 2500
          })
        } else if(duration < 1) {
          wx.showToast({
            title: '上传的视频不能小于1秒···',
            icon: "none",
            duration: 2500
          })
        } else {
          // 进入到bgm选择页面
          wx.navigateTo({
            url: '../chooseBgm/chooseBgm?duration=' + duration
              + "&tmpHeight=" + tmpHeight
              + "&tmpWidth=" + tmpWidth
              + "&tmpVideoUrl=" + tmpVideoUrl
              + "&tmpVideoUrl=" + tmpVideoUrl
              + "&tmpConverUrl=" + tmpConverUrl,
              
          })
        }
      }
    })
  }
})
