function varargout = testingui(varargin)
% TESTINGUI MATLAB code for testingui.fig
%      TESTINGUI, by itself, creates a new TESTINGUI or raises the existing
%      singleton*.
%
%      H = TESTINGUI returns the handle to a new TESTINGUI or the handle to
%      the existing singleton*.
%
%      TESTINGUI('CALLBACK',hObject,eventData,handles,...) calls the local
%      function named CALLBACK in TESTINGUI.M with the given input arguments.
%
%      TESTINGUI('Property','Value',...) creates a new TESTINGUI or raises the
%      existing singleton*.  Starting from the left, property value pairs are
%      applied to the GUI before testingui_OpeningFcn gets called.  An
%      unrecognized property name or invalid value makes property application
%      stop.  All inputs are passed to testingui_OpeningFcn via varargin.
%
%      *See GUI Options on GUIDE's Tools menu.  Choose "GUI allows only one
%      instance to run (singleton)".
%
% See also: GUIDE, GUIDATA, GUIHANDLES

% Edit the above text to modify the response to help testingui

% Last Modified by GUIDE v2.5 02-Aug-2015 13:55:49

% Begin initialization code - DO NOT EDIT
gui_Singleton = 1;
gui_State = struct('gui_Name',       mfilename, ...
                   'gui_Singleton',  gui_Singleton, ...
                   'gui_OpeningFcn', @testingui_OpeningFcn, ...
                   'gui_OutputFcn',  @testingui_OutputFcn, ...
                   'gui_LayoutFcn',  [] , ...
                   'gui_Callback',   []);
if nargin && ischar(varargin{1})
    gui_State.gui_Callback = str2func(varargin{1});
end

if nargout
    [varargout{1:nargout}] = gui_mainfcn(gui_State, varargin{:});
else
    gui_mainfcn(gui_State, varargin{:});
end
% End initialization code - DO NOT EDIT


% --- Executes just before testingui is made visible.
function testingui_OpeningFcn(hObject, eventdata, handles, varargin)
% This function has no output args, see OutputFcn.
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
% varargin   command line arguments to testingui (see VARARGIN)

% Choose default command line output for testingui
handles.output = hObject;

xcord = -80.5429439;
ycord = 43.4720646;
handles.xcord = xcord;
handles.ycord = ycord;

% (Longitude, Latitude)
%handles.current_data = [handles.xcord, handles.ycord; -79.383184 43.653226];
handles.current_data = [handles.xcord, handles.ycord; -80.5020670 43.5191718];

% Update handles structure
guidata(hObject, handles);


% UIWAIT makes testingui wait for user response (see UIRESUME)
% uiwait(handles.figure1);


% --- Outputs from this function are returned to the command line.
function varargout = testingui_OutputFcn(hObject, eventdata, handles) 
% varargout  cell array for returning output args (see VARARGOUT);
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Get default command line output from handles structure
varargout{1} = handles.output;


% --- Executes when selected object is changed in uibuttongroup1.
function uibuttongroup1_SelectionChangedFcn(hObject, eventdata, handles)
% hObject    handle to the selected object in uibuttongroup1 
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
h=get(handles.uibuttongroup1,'SelectedObject');
display(['Current: ' get(h,'String')]);
if strcmp(get(h,'String'), 'University of Waterloo')
   handles.xcord = -80.5429439;
   handles.ycord = 43.4720646;
elseif strcmp(get(h,'String'), 'Rim Park')
   handles.xcord = -80.5020670;
   handles.ycord = 43.5191718;
elseif strcmp(get(h,'String'), 'Grand River Park')
   handles.xcord = -80.5116800;
   handles.ycord = 43.4557709;
handles.current_data(1,1) = handles.xcord;
handles.current_data(1,2) = handles.ycord;
handles.current_data(end,1) = handles.xcord;
handles.current_data(end,2) = handles.ycord;
end


% --- Executes on selection change in popupmenu1.
function popupmenu1_Callback(hObject, eventdata, handles)
% hObject    handle to popupmenu1 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA
handles.current_data(end,:) = [];
switch get(handles.popupmenu1,'Value') 
  case 1
    Arr2=[-79.6441198, 43.5890452];
    handles.current_data=vertcat(handles.current_data, Arr2, handles.current_data([1],:))
  case 2
    Arr2=[-79.6441198, 43.5890452];
    handles.current_data=vertcat(handles.current_data, Arr2, handles.current_data([1],:))
    %handles.current_data=[handles.current_data(1:end - 1); [-79.6441198, 43.5890452] ;handles.current_data(end:end)];
  case 3
    Arr2=[-79.6441198, 43.5890452];
    handles.current_data=vertcat(handles.current_data, Arr2, handles.current_data([1],:))
  case 4
    Arr2=[-79.6441198, 43.5890452];
    handles.current_data=vertcat(handles.current_data, Arr2, handles.current_data([1],:))
  otherwise  
end 
display(['Current: ' num2str(get(handles.popupmenu1,'Value'))]);
display(['Data: ' mat2str(handles.current_data)]);
% Hints: contents = cellstr(get(hObject,'String')) returns popupmenu1 contents as cell array
%        contents{get(hObject,'Value')} returns selected item from popupmenu1


% --- Executes during object creation, after setting all properties.
function popupmenu1_CreateFcn(hObject, eventdata, handles)
% hObject    handle to popupmenu1 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: popupmenu controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Executes on button press in add.
function add_Callback(hObject, eventdata, handles)
% hObject    handle to add (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)


% --- Executes when uibuttongroup1 is resized.
function uibuttongroup1_SizeChangedFcn(hObject, eventdata, handles)
% hObject    handle to uibuttongroup1 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)


% --- If Enable == 'on', executes on mouse press in 5 pixel border.
% --- Otherwise, executes on mouse press in 5 pixel border or over add.
function add_ButtonDownFcn(hObject, eventdata, handles)
% hObject    handle to add (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)


% --- Executes on button press in show.
function show_Callback(hObject, eventdata, handles)
% hObject    handle to show (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
display(['Data: ' mat2str(handles.current_data)]);

f = figure(2);
set (f, 'Units', 'normalized', 'Position', [0,0,1,1]);
cla reset;

% plot route data
plot(handles.current_data(:, 1), handles.current_data(:, 2), 'r', 'LineWidth', 2);

% Begining marker
line(handles.current_data(1, 1), handles.current_data(1, 2), 'Marker', 'o', ...
    'Color', 'b', 'MarkerFaceColor', 'b', 'MarkerSize', 10);

% Ending marker
line(handles.current_data(end, 1), handles.current_data(end, 2), 'Marker', 's', ...
    'Color', 'b', 'MarkerFaceColor', 'b', 'MarkerSize', 10);

% hide axis display
% xlim([-71.4, -71]); axis equal off

% Google map

plot_google_map('maptype', 'roadmap');

% --- If Enable == 'on', executes on mouse press in 5 pixel border.
% --- Otherwise, executes on mouse press in 5 pixel border or over show.
function show_ButtonDownFcn(hObject, eventdata, handles)
% hObject    handle to show (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
