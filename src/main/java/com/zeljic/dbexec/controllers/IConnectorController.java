package com.zeljic.dbexec.controllers;

import com.zeljic.dbexec.db.connectors.IConnector;

public interface IConnectorController
{
	IConnector getConnector();
}