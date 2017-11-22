/*
 *  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.ballerinalang.net.http;

import org.ballerinalang.connector.api.BallerinaConnectorException;
import org.ballerinalang.connector.api.ConnectorFutureListener;
import org.ballerinalang.model.values.BBoolean;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.net.http.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.transport.http.netty.message.HTTPCarbonMessage;

/**
 * {@code HttpConnectorFutureListener} is the responsible for acting on notifications received from Ballerina side.
 *
 * @since 0.94
 */
public class HttpConnectorFutureListener implements ConnectorFutureListener {
    private static final Logger log = LoggerFactory.getLogger(HttpConnectorFutureListener.class);
    private HTTPCarbonMessage requestMessage;
    private BValue request;

    public HttpConnectorFutureListener(HTTPCarbonMessage requestMessage, BValue request) {
        this.requestMessage = requestMessage;
        this.request = request;
    }

    @Override
    public void notifySuccess() {
        //nothing to do, this will get invoked once resource finished execution
    }

    @Override
    public void notifyReply(BValue... response) {
        HTTPCarbonMessage responseMessage = HttpUtil
                .getCarbonMsg((BStruct) response[0], HttpUtil.createHttpCarbonMessage(false));

        boolean keepAlive = ((BBoolean) response[1]).booleanValue();
        if (!keepAlive) {
            responseMessage.setHeader("Connection", "close");
        }

        Session session = (Session) ((BStruct) request).getNativeData(Constants.HTTP_SESSION);
        if (session != null) {
            session.generateSessionHeader(responseMessage);
        }
        //Process CORS if exists.
        if (requestMessage.getHeader("Origin") != null) {
            CorsHeaderGenerator.process(requestMessage, responseMessage, true);
        }
        HttpUtil.handleResponse(requestMessage, responseMessage);
    }

    @Override
    public void notifyFailure(BallerinaConnectorException ex) {
        HttpUtil.handleFailure(requestMessage, ex);
    }
}
