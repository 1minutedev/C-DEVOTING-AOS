(function($, undefined){

    window.cVotingUtil = {
	};


    cVotingUtil.callPlugin = function(id, param){
        var bridge = window.KKJBridge;

        var data = {
            "id" : id,
            "param" : param
        };

        bridge.callPlugin(JSON.stringify(data));
    }

    cVotingUtil.testPlugin = function(){
        var param = {
            test_data : "data",
            callback : "callback" //콜백 받을 함수 명
        };

        cVotingUtil.callPlugin("TEST", param);
    }

    cVotingUtil.showDiscussionPage = function(data) {
        
        console.log("showDiscussionPage " , JSON.stringify(data));

        if (data !== null && data !== undefined && data !== '') {
            var param = {
                page_data :data 
                // callback : "callback" //콜백 받을 함수 명
            };
        
            cVotingUtil.callPlugin("SHOW_DISCUSSION", param);
        }   
            
    }

    cVotingUtil.callback = function(result) {
        console.log(JSON.stringify(result));
    }

    cVotingUtil.setStorage = function(type, listName, data) {
        var getData_url = ""

        if (type === "initiative_regist") {
            getData_url = "InitiativeRegistrationComponent.setDataCallback";
        } else if(type === "initiative") {
            getData_url = "InitiativeComponent.setDataCallback";
        };

        var param = {
            storage_name : "default",
            list_name : listName,
            callback : getData_url, //콜백 받을 함수 명
            data : $.parseJSON(data)
        };
        
        cVotingUtil.callPlugin("SET_SHARED_STORAGE", param);
    }

    cVotingUtil.getStorage = function(type, listName) {

        var getData_url = ""

        if (type === "main") {
            getData_url = "CVotingMainComponent.getData";
        } else if (type === "initiative_regist") {
            getData_url = "InitiativeRegistrationComponent.getDataCallback";
        } else if (type === "initiative") {
            getData_url = "InitiativeComponent.getDataCallback";
        } else if (type === "search") {
            getData_url = "CVotingSearchComponent.getData";
        }

        var res = {"result" : true};
        var param = {
            storage_name : "default",
            list_name : listName, // InitiativeList ReviewList VoteList
            callback: getData_url
        }
        
//        console.log('@@@ GET DATA param >>> ' + JSON.stringify(param));
        cVotingUtil.callPlugin("GET_SHARED_STORAGE", param);

    }

    cVotingUtil.getImagePath = function() {
        var param = {
            callback : "InitiativeRegistrationComponent.setImgPath" //콜백 받을 함수 명
        };

        cVotingUtil.callPlugin("GET_IMAGE_PICK", param);
    }


})(jQuery, undefined);