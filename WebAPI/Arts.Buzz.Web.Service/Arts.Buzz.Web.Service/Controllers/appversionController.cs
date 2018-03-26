using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;

namespace Arts.Buzz.Web.Service.Controllers
{
    public class appversionController : BaseApiController
    {
        // GET api/appversion
        public IEnumerable<appversion> Getversions()
        {
            return db.appversion.AsEnumerable();
        }

        // GET api/appversion/5
        public appversion Getversion(int id)
        {
            appversion version = db.appversion.Find(id);
            if (version == null)
            {
                throw new HttpResponseException(Request.CreateResponse(HttpStatusCode.NotFound));
            }

            return version;
        }

        // PUT api/appversion/5
        public HttpResponseMessage Putversion(int id, appversion version)
        {
            if (!ModelState.IsValid)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ModelState);
            }

            if (id != version.id)
            {
                return Request.CreateResponse(HttpStatusCode.BadRequest);
            }

            db.Entry(version).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.NotFound, ex);
            }

            return Request.CreateResponse(HttpStatusCode.OK);
        }

        // POST api/appversion
        public HttpResponseMessage Postversion(appversion version)
        {
            if (ModelState.IsValid)
            {
                db.appversion.Add(version);
                db.SaveChanges();

                HttpResponseMessage response = Request.CreateResponse(HttpStatusCode.Created, version);
                response.Headers.Location = new Uri(Url.Link("DefaultApi", new { id = version.id }));
                return response;
            }
            else
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ModelState);
            }
        }

        // DELETE api/appversion/5
        public HttpResponseMessage Deleteversion(int id)
        {
            appversion version = db.appversion.Find(id);
            if (version == null)
            {
                return Request.CreateResponse(HttpStatusCode.NotFound);
            }

            db.appversion.Remove(version);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.NotFound, ex);
            }

            return Request.CreateResponse(HttpStatusCode.OK, version);
        }

        protected override void Dispose(bool disposing)
        {
            db.Dispose();
            base.Dispose(disposing);
        }
    }
}