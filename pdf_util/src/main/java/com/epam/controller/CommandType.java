package com.epam.controller;


import com.epam.controller.handler.CommandHandler;
import com.epam.controller.handler.PdfCommandHandler;
import com.epam.controller.handler.ReportCommandHandler;
import com.epam.controller.handler.ReportToPdfCommandHandler;

/**
 * Class uses for description of supported methods. Uses to bind enum value, which defined in {@link com.epam.model.Command command} bean, with handler class,
 * which must implement {@link com.epam.controller.handler.CommandHandler CommandHandler} interface
 */
public enum CommandType {

    FIND_IDS_IN_REPORTS(ReportCommandHandler.class),
    FIND_IDS_IN_PDF(PdfCommandHandler.class),
    FIND_IDS_FROM_REPORTS_IN_PDF(ReportToPdfCommandHandler.class);

    private Class<? extends CommandHandler> handler;

    private CommandType(Class<? extends CommandHandler> handler) {
        this.handler = handler;
    }

    public Class<? extends CommandHandler> getHandler() {
        return handler;
    }

}
