package com.grepp.spring.app.controller.api.mainpage.payload;

import java.util.ArrayList;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowGroupResponse {
  private ArrayList<Long> groupIds;
}
