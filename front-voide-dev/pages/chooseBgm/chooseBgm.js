const app = getApp()

Page({
    data: {
      bgmList:[],
      serverUrl:'',
      videoParams: ''
    },

    onLoad: function (params) {
      // 使用 wx.createAudioContext 获取 audio 上下文 context
      var we = this;
      this.audioCtx = wx.createAudioContext('myAudio')
      console.log(params)
      we.setData({
        videoParams: params
      })
      wx.showLoading({
        title: '加载中···',
      })
      // 查询bgm列表
      wx.request({
        url: app.serverUrl + '/bgm/list',
        method: 'POST',
        header: {
          'content-type': 'application/json' // 默认值
        },
        success: function (res) {
          we.setData({
            bgmList:res.data.data,
            serverUrl:app.serverUrl
          })
          wx.hideLoading({})
        }
      })
    },
    // 上传视频
    upload (e) {
      var me = this
      var bgmId = e.detail.value.bgmId
      var desc = e.detail.value.desc
      var duration = me.data.videoParams.duration
      var tmpHeight = me.data.videoParams.tmpHeight
      var tmpWidth = me.data.videoParams.tmpWidth
      var tmpVideoUrl = me.data.videoParams.tmpVideoUrl
      var tmpConverUrl = me.data.videoParams.tmpConverUrl

      console.log(desc)
      console.log('tmpConverUrl=====' + tmpConverUrl)
      wx.showLoading({
        title: '上传中···',
      })
      var serverUrl = app.serverUrl
      console.log('开始上传视频！')
      wx.uploadFile({
        url: serverUrl + '/video/uplode',
        formData: {
          userId: app.userInfo.id,
          bgmId: bgmId,
          des: desc,
          videoSeconds: duration,
          videoWidth: tmpWidth,
          videoHeight: tmpHeight
        },
        // 文件流
        filePath: tmpVideoUrl,
        name: 'file',
        header: {
          'content-type': 'application/json' // 默认值
        },
        success(res) {
          console.log(res)
          console.log('视频上传成功')
          var data = JSON.parse(res.data);
          if (data.status == 200) {
            wx.showToast({
              title: '上传成功···',
              icon: 'success'
            })
            wx.navigateBack({
              delta:1
            })
            // var videoId = data.data
            // wx.showLoading({
            //   title: '上传中···',
            // })
            // console.log('开始上传头像')
            // wx.uploadFile({
            //   url: serverUrl + '/video/uplodeCover',
            //   formData: {
            //     userId: app.userInfo.id,
            //     videoId : videoId
            //   },
            //   filePath: tmpConverUrl,
            //   name: 'file',
            //   header: {
            //     'content-type': 'application/json' // 默认值
            //   },
            //   success(res) {
            //     var data = JSON.parse(res.data);
            //     if (data.status == 200) {
            //       console.log('头像上传成功')
            //       wx.showToast({
            //         title: '上传成功···',
            //         icon: 'success'
            //       })
            //       wx.navigateBack({
            //         delta:1
            //       })
            //     } else {
            //       wx.showToast({
            //         title: '上传失败！···',
            //         icon: 'success'
            //       })
            //     }
            //   }
            // })
          } else {
            wx.showToast({
              title: '上传失败！···',
              icon: 'success'
            })
          }
        }
      })
    }
})

